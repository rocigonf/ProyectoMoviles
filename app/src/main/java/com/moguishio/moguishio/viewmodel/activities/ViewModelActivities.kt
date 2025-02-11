package com.moguishio.moguishio.viewmodel.activities

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moguishio.moguishio.model.activities.ActivitiesRepository
import com.moguishio.moguishio.model.activities.ActivityResponse
import com.moguishio.moguishio.model.activities.ParticipationRequest
import com.moguishio.moguishio.model.activities.ParticipationResponse
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth

class ViewModelActivities : ViewModel() {
    private val activity : ActivitiesRepository = ActivitiesRepository()

    private val companion = ViewModelAuth.Companion

    private val _activities = MutableLiveData<List<ActivityResponse>>()
    val activities : LiveData<List<ActivityResponse>> = _activities

    private val _activityList = MutableLiveData<List<Activity>>()
    val activityList : LiveData<List<Activity>> = _activityList

    private val _participations = MutableLiveData<List<ParticipationResponse>>()
    val participations : LiveData<List<ParticipationResponse>> = _participations

    suspend fun getAllActivities() {
        val allActivities = activity.findAll(companion.ACCESS_TOKEN.toString())
        _activities.value = allActivities!!
    }

    suspend fun getUserActivities(userId: Int){
        val userActivity = activity.findByUserId(companion.ACCESS_TOKEN.toString(), userId)
        _activityList.value = userActivity!!
    }

    suspend fun createParticipation(activityId: Int){
        val userId = companion.ID.toString().toInt()
        activity.create(companion.ACCESS_TOKEN.toString(), ParticipationRequest(userId, activityId))
        getUserActivities(userId)
    }

    suspend fun deleteParticipation(activityId: Int){
        val userId = companion.ID.toString().toInt()
        activity.deleteById(companion.ACCESS_TOKEN.toString(), activityId)
        getUserActivities(userId)
    }
}