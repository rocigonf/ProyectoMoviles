package com.moguishio.moguishio.model.activities

import android.util.Log

class ActivitiesRepository {
    private val activityClient = RetrofitActivityInstance.client

    suspend fun findAll(token: String): List<ActivityResponse>? {
        val newToken = "Bearer $token"
        return activityClient.findAll(newToken).body()
    }

    suspend fun findByUserId(token: String, userId: Int): List<ActivityResponse>? {
        val newToken = "Bearer $token"
        return activityClient.findByUserId(newToken, userId).body()
    }

    suspend fun create(token: String, participationRequest: ParticipationRequest) {
        val newToken = "Bearer $token"
        Log.e("CREATE", newToken)
        activityClient.create(newToken, participationRequest)
    }

    suspend fun deleteById(token: String, userId: Int, activityId: Int): Boolean? {
        val newToken = "Bearer $token"
        return activityClient.deleteById(newToken, userId, activityId).body()
    }
}