package com.moguishio.BackendPersonalizado.controller.activity

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.model.Participation
import com.moguishio.BackendPersonalizado.services.ActivityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/activity")
class ActivityController(
    private val activityService: ActivityService
) {

    @PostMapping
    fun create(@RequestBody activityRequest: ActivityRequest): ActivityResponse? {
        val newActivity = activityService.create(activityRequest.toModel())
        return newActivity.toResponse()
    }

    @GetMapping("/{activityId}")
    fun findByActivityId(@PathVariable activityId: Int): Optional<Activity> =
        activityService.findById(activityId)

    @GetMapping
    fun findAll(): List<ActivityResponse> =
        activityService.findAll()
            .map { it.toResponse() }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int): ResponseEntity<Boolean> {
        val success = activityService.deleteById(id)

        return if (success.equals(true))
            ResponseEntity.noContent()
                .build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found.")
    }

    private fun Activity.toResponse(): ActivityResponse =
        ActivityResponse(
            id = this.id,
            name = this.name,
            place = this.place,
            description = this.description
        )

    private fun ActivityRequest.toModel(): Activity =
        Activity(
            id = 0,
            name = this.name,
            place = this.place,
            description = this.description
        )
}