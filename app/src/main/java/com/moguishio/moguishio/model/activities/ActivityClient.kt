package com.moguishio.moguishio.model.activities


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityClient {
    @GET("activity")
    suspend fun findAll(@Header("Authorization") authorization: String): Response<List<ActivityResponse>>

    @GET("participation/user/{userId}")
    suspend fun findByUserId(@Header("Authorization") authorization: String, @Path("userId") userId: Int): Response<List<ActivityResponse>>

    @POST("participation")
    suspend fun create(@Header("Authorization") authorization: String, @Body participationRequest: ParticipationRequest): Response<Unit>

    @DELETE("participation/{userId}/{activityId}")
    suspend fun deleteById(@Header("Authorization") authorization: String, @Path("userId") userId: Int, @Path("activityId") activityId: Int): Response<Boolean>
}