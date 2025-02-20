package com.moguishio.moguishio.viewmodel.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.moguishio.moguishio.AplicacionTareas
import com.moguishio.moguishio.model.activities.ActivitiesRepository
import com.moguishio.moguishio.model.activities.ActivityResponse
import com.moguishio.moguishio.model.activities.ParticipationResponse
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.dataStoreAuth
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.ACCESS_TOKEN
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ViewModelActivities(private val context: Context, private val authViewModel: ViewModelAuth) : ViewModel() {
    private val activity: ActivitiesRepository = ActivitiesRepository()

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _activities = MutableLiveData<List<ActivityResponse>>()
    val activities: LiveData<List<ActivityResponse>> = _activities

    private val _activityList = MutableLiveData<List<Activity>>()
    val activityList: LiveData<List<Activity>> = _activityList

    private val _participations = MutableLiveData<List<ParticipationResponse>>()
    val participations: LiveData<List<ParticipationResponse>> = _participations

    suspend fun getAllActivities() {

        val currentToken = _token.value
        Log.e("TOKEN1", currentToken.toString())
        Log.e("TOKEN2", token.value.toString())
        Log.e("TOKEN3", _token.value.toString())
        if (currentToken.isNullOrEmpty()) return

        val allActivities = activity.findAll("Bearer $currentToken")
        _activities.value = allActivities ?: emptyList()

        Log.e("ACTIVIDADES", "Obtenidas todas las actividades")
    }

    /*suspend fun getUserActivities(userId: Int) {
        val userActivity = activity.findByUserId(token, userId)
        _activityList.value = userActivity!!
    }

    suspend fun createParticipation(activityId: Int) {
        activity.create(token, ParticipationRequest(id.toInt(), activityId))
        getUserActivities(id.toInt())
    }

    suspend fun deleteParticipation(activityId: Int) {
        activity.deleteById(token, activityId)
        getUserActivities(id.toInt())
    }*/

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                ViewModelActivities(application.container.context, ViewModelAuth(application.container.context))
            }
        }
    }

    init {
        loadCredentials()
    }

    fun<T> getInfo(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStoreAuth.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    fun loadCredentials(){
        viewModelScope.launch {
            _token.value = context.dataStoreAuth.data
                .map { preferences ->
                    preferences[ACCESS_TOKEN] ?: ""
                }.first()

            //_token.value = getInfo(ACCESS_TOKEN, "").collect { _token.value = it }.toString()
            Log.e("token", token.value.toString())
        }
    }
}
