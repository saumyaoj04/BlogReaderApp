package com.example.blogreader.data.remote

import com.example.blogreader.data.model.BlogDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getBlogs(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<BlogDto>
}
