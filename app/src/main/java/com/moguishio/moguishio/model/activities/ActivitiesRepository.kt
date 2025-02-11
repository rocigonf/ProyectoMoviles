package com.moguishio.moguishio.model.activities

import android.app.Activity

class ActivitiesRepository {
    private val activityClient = RetrofitActivityInstance.client

    suspend fun findAll(token: String): List<ActivityResponse>? {
        return activityClient.findAll(token).body()
    }

    suspend fun findByUserId(token: String, userId: Int): List<Activity>? {
        return activityClient.findByUserId(token, userId).body()
    }

    suspend fun create(token: String, participationRequest: ParticipationRequest): ParticipationResponse? {
        return activityClient.create(token, participationRequest).body()
    }

    suspend fun deleteById(token: String, id: Int): Boolean? {
        return activityClient.deleteById(token, id).body()
    }
}