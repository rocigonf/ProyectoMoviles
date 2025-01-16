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
            "insert into users values (?, ?, ?)",
            user.email, updated.password, user.role.id
        )
    }

    fun findByEmail(email: String): User? =
        db.query("select * from users INNER JOIN roles ON users.roles_id = roles.id where users.email = ?", email) {
                response, _ ->
            val role = Role(response.getInt("response.roles.id"), response.getString("response.roles.name"))
            User(response.getInt("id"), response.getString("email"), response.getString("password"), role)
        }.singleOrNull()


    fun findAll(): List<User> =
        db.query("select * from users") { response, _ ->
            val role = Role(response.getInt("response.roles.id"), response.getString("response.roles.name"))
            User(response.getInt("id"), response.getString("email"), response.getString("password"), role)
        }

    fun findById(id: Int): User? =
        db.query("select * from users INNER JOIN roles ON users.roles_id = roles.id where users.id = ?", id) {
                response, _ ->
            val role = Role(response.getInt("response.roles.id"), response.getString("response.roles.name"))
            User(response.getInt("id"), response.getString("email"), response.getString("password"), role)
        }.singleOrNull()

    fun deleteByID(id: Int): Int {
        val foundUser = findById(id)
        return db.update("delete from users where id = ?", foundUser?.id)
    }
}