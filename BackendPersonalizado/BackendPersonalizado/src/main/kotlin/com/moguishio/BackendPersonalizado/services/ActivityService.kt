package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.repositories.ActivityRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ActivityService(
    private val activityRepository: ActivityRepository
) {

    fun create(activity: Activity): Activity {
        return activityRepository.save(activity)
    }

    fun findById(id: Int): Optional<Activity> =
        activityRepository.findById(id)

    fun findAll(): List<Activity> =
        activityRepository.findAll()
            .toList()

    fun deleteById(id: Int): Unit =
        activityRepository.deleteById(id)
}