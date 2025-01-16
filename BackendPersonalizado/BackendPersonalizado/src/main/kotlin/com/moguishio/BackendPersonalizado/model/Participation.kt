package com.moguishio.BackendPersonalizado.model

import org.springframework.data.relational.core.mapping.Table

@Table("participations")
data class Participation(
    val id: Int,
    val user: User?,
    val activity: Activity?
)
