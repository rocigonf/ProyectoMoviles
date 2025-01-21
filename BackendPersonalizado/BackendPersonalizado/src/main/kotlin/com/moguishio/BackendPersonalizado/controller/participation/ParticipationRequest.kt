package com.moguishio.BackendPersonalizado.controller.participation

data class ParticipationRequest(
    val userId: Int,
    val activityId: Int
)