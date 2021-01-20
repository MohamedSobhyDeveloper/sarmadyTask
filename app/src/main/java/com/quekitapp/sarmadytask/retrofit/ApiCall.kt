package com.quekitapp.gasloyalty.retrofit

import com.quekitapp.sarmadytask.model.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.HashMap


interface ApiCall {

    @GET("services/rest/?")
    fun getPhotos(@QueryMap requestBody: HashMap<String, String?>?): Call<MovieModel?>?

}