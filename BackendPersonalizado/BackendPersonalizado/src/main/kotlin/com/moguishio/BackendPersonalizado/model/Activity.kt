package com.moguishio.BackendPersonalizado.model

import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("activity")
data class Activity(
    val id: Int,
    val name : String
)
