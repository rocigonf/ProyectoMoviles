package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.repositories.ActivityRepository
import java.util.*

class ActivityService(private val activityRepository: ActivityRepository)
{
    fun findByUUID(uuid: UUID): Optional<Activity> = activityRepository.findById(uuid)

    fun findAll(): List<Activity> =
        activityRepository.findAll()
            .toList()

    fun deleteByUUID(uuid: UUID): Unit =
        activityRepository.deleteById(uuid)
}