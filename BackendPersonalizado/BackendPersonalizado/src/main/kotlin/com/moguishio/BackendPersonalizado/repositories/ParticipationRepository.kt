package com.moguishio.BackendPersonalizado.repositories

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.model.Participation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class ParticipationRepository(private val db: JdbcTemplate, private val userRepository: UserRepository, private val activityRepository: ActivityRepository) {
    fun save(participation: Participation): Int {
        return db.update(
            "insert into participations values (?, ?)",
            participation.user?.id, participation.activity?.id
        )
    }

    fun findByActivityId(activityId: Int): List<Participation>? =
        db.query("select * from participations INNER JOIN activites ON participations.activity_id = activities.id where activities.id = ?", activityId) {
                response, _ ->

            val user = userRepository.findById(response.getInt("user_id"))
            if (user != null) {
                Participation(response.getInt("id"), user, Activity(response.getInt("response.activities.id"), response.getString("response.activities.name")))
            }
            Participation(0, null, null)
        }

    fun findByUserId(userId: Int): List<Participation>? =
        db.query("select * from participations INNER JOIN activites ON participations.activity_id = activities.id where participations.user_id = ?", userId) {
                response, _ ->

            val user = userRepository.findById(response.getInt("user_id"))
            if (user != null) {
                Participation(response.getInt("id"), user, Activity(response.getInt("response.activities.id"), response.getString("response.activities.name")))
            }
            Participation(0, null, null)
        }


    fun findAll(): List<Participation> =
        db.query("select * from participations INNER JOIN activites ON participations.activity_id = activities.id") {
                response, _ ->

            val user = userRepository.findById(response.getInt("user_id"))
            if (user != null) {
                Participation(response.getInt("id"), user, Activity(response.getInt("response.activities.id"), response.getString("response.activities.name")))
            }
            Participation(0, null, null)
        }


    fun findById(id: Int): Participation? =
        db.query("select * from participations INNER JOIN activites ON participations.activity_id = activities.id where participations.id = ?", id) {
                response, _ ->

            val user = userRepository.findById(response.getInt("user_id"))
            if (user != null) {
                Participation(response.getInt("id"), user, Activity(response.getInt("response.activities.id"), response.getString("response.activities.name")))
            }
            Participation(0, null, null)

        }.singleOrNull()


    fun deleteByID(id: Int): Int {
        return db.update("delete from participations where id = ?", id)
    }
}