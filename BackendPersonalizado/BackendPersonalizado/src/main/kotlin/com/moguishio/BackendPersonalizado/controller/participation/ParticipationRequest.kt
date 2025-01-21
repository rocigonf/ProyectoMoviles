package com.moguishio.BackendPersonalizado.controller.participation

data class ParticipationRequest(
    val id: Int,
    val userId: Int,
    val activityId: Int
)