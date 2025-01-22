package com.moguishio.BackendPersonalizado.controller.participation

import com.moguishio.BackendPersonalizado.model.Activity
import java.util.*

data class ParticipationResponse(
    val id: Int,
    val userId: Int,
    val activityId: Int
)