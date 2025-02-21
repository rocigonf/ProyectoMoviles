package com.moguishio.moguishio.viewmodel.activities

//import com.moguishio.moguishio.model.activities.ParticipationResponse
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import com.moguishio.moguishio.model.activities.ParticipationRequest
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.ACCESS_TOKEN
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.ID
import com.moguishio.moguishio.viewmodel.authentication.ViewModelAuth.Companion.dataStoreAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ViewModelActivities(private val context: Context) : ViewModel() {
    private val activity: ActivitiesRepository = ActivitiesRepository()

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _id = MutableLiveData<String>()
    val id: LiveData<String> = _id

    private val _activities = MutableLiveData<List<ActivityResponse>>()
    val activities: LiveData<List<ActivityResponse>> = _activities

    private val _userActivities = MutableLiveData<List<ActivityResponse>>()
    val userActivities: LiveData<List<ActivityResponse>> = _userActivities

    /*private val _participations = MutableLiveData<List<ParticipationResponse>>()
    val participations: LiveData<List<ParticipationResponse>> = _participations*/

    suspend fun getAllActivities() {
        val currentToken = _token.value
        Log.e("TOKEN1", currentToken.toString())
        Log.e("TOKEN2", token.value.toString())
        Log.e("TOKEN3", _token.value.toString())

        if (currentToken.isNullOrEmpty()) {
            return
        }

        val allActivities = activity.findAll(currentToken)
        _activities.value = allActivities ?: emptyList()

        Log.e("ACTIVIDADES", "Obtenidas todas las actividades")
    }

    suspend fun getUserActivities() {
        val currentToken = _token.value
        if (currentToken.isNullOrEmpty()) {
            return
        }

        val currentId = _id.value!!.toInt()
        val userActivity = activity.findByUserId(currentToken, currentId)
        _userActivities.value = userActivity ?: emptyList()
    }

    suspend fun createParticipation(activityId: Int) {
        val currentToken = _token.value!!
        val currentId = _id.value!!.toInt()
        activity.create(currentToken, ParticipationRequest(currentId, activityId))
        getUserActivities()
    }

    suspend fun deleteParticipation(activityId: Int) {
        val currentToken = _token.value!!
        val currentId = _id.value!!.toInt()
        activity.deleteById(currentToken, currentId, activityId)
        //getUserActivities()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AplicacionTareas)
                //ViewModelActivities(application.container.context, ViewModelAuth(application.container.context))
                ViewModelActivities(application.container.context)
            }
        }
    }

    init {
        loadCredentials()
    }

    fun loadCredentials(){
        viewModelScope.launch {
            _token.value = context.dataStoreAuth.data
                .map { preferences ->
                    preferences[ACCESS_TOKEN] ?: ""
                }.first()

            _id.value = context.dataStoreAuth.data
                .map { preferences ->
                    preferences[ID] ?: ""
                }.first()

            //_token.value = getInfo(ACCESS_TOKEN, "").collect { _token.value = it }.toString()
            Log.e("token", token.value.toString())
            Log.e("token", token.value.toString())
        }
    }
}
