package com.moguishio.moguishio.viewmodel.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moguishio.moguishio.AplicacionTareas
import com.moguishio.moguishio.model.activities.ActivitiesRepository
import com.moguishio.moguishio.model.activities.ActivityResponse
import com.moguishio.moguishio.model.activities.ParticipationRequest
import com.moguishio.moguishio.model.activities.ParticipationResponse
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth

@SuppressLint("StaticFieldLeak")
class ViewModelActivities(private val context: Context) : ViewModel() {
    private val activity : ActivitiesRepository = ActivitiesRepository()

    private val token = "Bearer ${ViewModelAuth(context).accessToken}"
    private val id = "Bearer ${ViewModelAuth(context).id}"

    private val _activities = MutableLiveData<List<ActivityResponse>>()
    val activities : LiveData<List<ActivityResponse>> = _activities

    private val _activityList = MutableLiveData<List<Activity>>()
    val activityList : LiveData<List<Activity>> = _activityList

    private val _participations = MutableLiveData<List<ParticipationResponse>>()
    val participations : LiveData<List<ParticipationResponse>> = _participations

    suspend fun getAllActivities() {
        val allActivities = activity.findAll(token)
        _activities.value = allActivities!!
    }

    suspend fun getUserActivities(userId: Int){
        val userActivity = activity.findByUserId(token, userId)
        _activityList.value = userActivity!!
    }

    suspend fun createParticipation(activityId: Int){
        activity.create(token, ParticipationRequest(id.toInt(), activityId))
        getUserActivities(id.toInt())
    }

    suspend fun deleteParticipation(activityId: Int){
        activity.deleteById(token, activityId)
        getUserActivities(id.toInt())
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                ViewModelActivities(application.container.context)
            }
        }
    }
}