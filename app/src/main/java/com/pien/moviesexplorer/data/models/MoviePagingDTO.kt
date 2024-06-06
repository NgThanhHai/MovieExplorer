package com.pien.moviesexplorer.data.models

import com.google.gson.annotations.SerializedName

data class MoviePagingDTO(
    @SerializedName("page") var page         : Int?               = null,
    @SerializedName("results") var results      : ArrayList<MovieDTO> = arrayListOf(),
    @SerializedName("total_pages") var totalPages   : Int?               = null,
    @SerializedName("total_results") var totalResults : Int?               = null
)