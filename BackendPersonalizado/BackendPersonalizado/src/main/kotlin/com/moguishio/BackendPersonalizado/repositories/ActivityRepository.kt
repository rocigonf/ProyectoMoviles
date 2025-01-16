package com.moguishio.BackendPersonalizado.repositories

import com.moguishio.BackendPersonalizado.model.Activity
import org.springframework.data.repository.CrudRepository

interface ActivityRepository : CrudRepository<Activity, Int> {
}