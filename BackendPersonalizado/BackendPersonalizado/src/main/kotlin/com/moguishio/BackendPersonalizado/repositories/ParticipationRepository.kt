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
            "insert into participations(activity_id, user_id) values (?, ?)",
            participation.activityId, participation.userId
        )
    }

    fun findByActivityId(activityId: Int): List<Participation>? =
        db.query("select * from participations where activity_id = ?", activityId) {
                response, _ ->
            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))
        }

    fun findByActivityAndUser(activityId: Int, userId: Int): Participation? =
        db.query("select * from participations where activity_id = ? and user_id = ?", activityId, userId) {
                response, _ ->
            Participation(response.getInt("id"), response.getInt("user_id"), response.getInt("activity_id"))
        }.singleOrNull()

    fun findByUserId(userId: Int): List<Activity>? =
        db.query("select * from activities INNER JOIN participations ON activities.id = participations.activity_id where participations.user_id = ?", userId) {
                response, _ ->
            Activity(response.getInt("activities.id"), response.getString("name"), response.getString("description"), response.getString("place"))
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


    fun deleteByID(userId: Int, activityId: Int): Int {
        return db.update("delete from participations where user_id = ? and activity_id = ? LIMIT 1", userId, activityId)
    }
}