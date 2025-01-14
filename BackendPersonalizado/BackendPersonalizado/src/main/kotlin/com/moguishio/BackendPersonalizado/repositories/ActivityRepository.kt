package com.moguishio.BackendPersonalizado.repositories

import com.moguishio.BackendPersonalizado.model.Activity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ActivityRepository : CrudRepository<Activity, UUID> {
}