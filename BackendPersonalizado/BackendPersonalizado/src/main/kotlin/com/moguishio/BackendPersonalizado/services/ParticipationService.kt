package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.model.Participation
import com.moguishio.BackendPersonalizado.repositories.ActivityRepository
import com.moguishio.BackendPersonalizado.repositories.ParticipationRepository
import org.springframework.stereotype.Service

@Service
class ParticipationService(
    private val participationRepository: ParticipationRepository
) {

    fun createParticipation(participation: Participation): Participation? {
        val saved = participationRepository.save(participation)
        return if (saved > 0) participation else null
    }

    fun findById(id: Int): Participation? =
        participationRepository.findById(id)

    fun findByActivityId(activityId: Int): List<Participation>? =
        participationRepository.findByActivityId(activityId)?.toList()

    fun findByUserId(userId: Int): List<Participation>? =
        participationRepository.findByUserId(userId)?.toList()

    fun findAll(): List<Participation> =
        participationRepository.findAll().toList()

    fun deleteById(id: Int): Int =
        participationRepository.deleteByID(id)
}