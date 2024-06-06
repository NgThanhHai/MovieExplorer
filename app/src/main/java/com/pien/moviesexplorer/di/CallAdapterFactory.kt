package com.pien.moviesexplorer.di

import com.pien.moviesexplorer.data.reponse.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CallAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Flow::class.java) {
            return null
        }
        if (returnType !is ParameterizedType) {
            return null
        }
        val flowType = getParameterUpperBound(0, returnType)
        val rawFlowType = getRawType(flowType)
        if (rawFlowType != ApiResponse::class.java) {
            return null
        }

        return FlowResourceCallAdapter<Any>(flowType)
    }
}
class FlowResourceCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, Flow<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> = flow {

        emit(ApiResponse.Loading)

        val resp = call.awaitResponse()

        if (resp.isSuccessful) {
            resp.body()?.let { data ->
                // Success
                emit(ApiResponse.Success(data))
            } ?: kotlin.run {
                // Error
                emit(ApiResponse.Error(Throwable(resp.message())))
            }
        } else {
            // Error
            val errorBody = resp.message()
            emit(ApiResponse.Error(Throwable(errorBody)))
        }

    }.catch { error ->
        emit(ApiResponse.Error(error))
    }
}