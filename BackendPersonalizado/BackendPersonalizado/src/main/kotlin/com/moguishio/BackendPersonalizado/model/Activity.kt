package com.moguishio.BackendPersonalizado.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("ACTIVITIES")
data class Activity(
    @Id val id: Int,
    val name : String
)
