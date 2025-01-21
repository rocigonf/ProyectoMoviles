package com.moguishio.BackendPersonalizado.controller.participation

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.model.Participation
import com.moguishio.BackendPersonalizado.services.ActivityService
import com.moguishio.BackendPersonalizado.services.ParticipationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/participation")
class ParticipationController(
    private val participationService: ParticipationService,
    private val activityService: ActivityService
) {

    @PostMapping
    fun create(@RequestBody participationRequest: ParticipationRequest): ParticipationResponse? {
        participationService.createParticipation(participationRequest.toModel())
        val newParticipation = participationService.findByActivityAndUser(participationRequest.activityId, participationRequest.userId)
        return newParticipation?.toResponse()
    }

    /*@GetMapping("/activity/{activityId}")
    fun findByActivityId(@PathVariable activityId: Int): List<Participation>? =
        participationService.findByActivityId(activityId)
    */

    @GetMapping("/user/{userId}")
    fun findByUserId(@PathVariable userId: Int): List<Activity>? =
        participationService.findByUserId(userId)

    @GetMapping
    fun findAll(): List<ParticipationResponse> =
        participationService.findAll()
            .map { it.toResponse() }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int): ResponseEntity<Boolean> {
        val success = participationService.deleteById(id)

        return if (success == 1)
            ResponseEntity.noContent()
                .build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Participation not found.")
    }

    private fun Participation.toResponse(): ParticipationResponse =
        ParticipationResponse(
            id = this.id,
            userId = this.userId,
            activityId = this.activityId,
            activity = activityService.findById(this.activityId)
        )

    private fun ParticipationRequest.toModel(): Participation =
        Participation(
            id = 0,
            userId = this.userId,
            activityId = this.activityId
        )
}