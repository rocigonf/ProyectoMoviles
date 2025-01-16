package com.moguishio.BackendPersonalizado.repositories

import com.moguishio.BackendPersonalizado.model.Activity
import com.moguishio.BackendPersonalizado.model.Participation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Repository

@Repository
class ParticipationRepository(private val db: JdbcTemplate) {
    fun save(participation: Participation): Int {
        return db.update(
            "insert into participations values (?, ?)",
            participation.userId, participation.activityId
        )
    }

    fun findByActivityId(activityId: Int): List<Participation>? =
        db.query("select * from participations where id = ?", activityId) {
                response, _ ->
            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))
        }

    fun findByUserId(userId: Int): List<Participation>? =
        db.query("select * from participations where user_id = ?", userId) {
                response, _ ->
            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))
        }


    fun findAll(): List<Participation> =
        db.query("select * from participations") {
                response, _ ->

            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))
        }


    fun findById(id: Int): Participation? =
        db.query("select * from participations where id = ?", id) {
                response, _ ->

            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))

        }.singleOrNull()


    fun deleteByID(id: Int): Int {
        return db.update("delete from participations where id = ?", id)
    }
}