package com.moguishio.BackendPersonalizado.repositories

import com.moguishio.BackendPersonalizado.model.Role
import com.moguishio.BackendPersonalizado.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val encoder: PasswordEncoder,
    private val db: JdbcTemplate
) {
    fun save(user: User): Int {
        val updated = user.copy(password = encoder.encode(user.password))
        return db.update(
            "insert into users(email, password, role_id) values (?, ?, ?)",
            user.email, updated.password, user.roleId
        )
    }

    fun findByEmail(email: String): User? =
        db.query("select * from users where email = ?", email) {
                response, _ ->
            User(response.getInt("id"), response.getString("email"), response.getString("password"), response.getInt("role_id"))
        }.singleOrNull()


    fun findAll(): List<User> =
        db.query("select * from users") { response, _ ->
            User(response.getInt("id"), response.getString("email"), response.getString("password"), response.getInt("role_id"))
        }

    fun updateUser(user: User): Int {
        if (user.password != "")
        {
            return db.update("UPDATE users SET email = ? AND password = ? AND role_id = ? WHERE id = ?", user.email, user.password, user.roleId, user.id)
        }
        return db.update("UPDATE users SET email = ? AND role_id = ? WHERE id = ?", user.email, user.roleId, user.id)
    }

    fun findById(id: Int): User? =
        db.query("select * from users where id = ?", id) {
                response, _ ->
            User(response.getInt("id"), response.getString("email"), response.getString("password"), response.getInt("role_id"))
        }.singleOrNull()

    fun deleteByID(id: Int): Int {
        val foundUser = findById(id)
        return db.update("delete from users where id = ?", foundUser?.id)
    }
}