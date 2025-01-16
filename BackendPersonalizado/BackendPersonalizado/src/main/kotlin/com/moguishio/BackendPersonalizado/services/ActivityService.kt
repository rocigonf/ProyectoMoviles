package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.repositories.ActivityRepository
import java.util.*

class ActivityService(private val activityRepository: ActivityRepository)
{
    fun findById(id: Int): Optional<Activity> = activityRepository.findById(id)

    fun findAll(): List<Activity> =
        activityRepository.findAll()
            .toList()

    fun deleteById(id: Int): Unit =
        activityRepository.deleteById(id)
}