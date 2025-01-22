package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.model.User
import com.moguishio.BackendPersonalizado.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(user: User): User? {
        val found = userRepository.findByEmail(user.email)

        return if (found == null) {
            userRepository.save(user)
            user
        } else null
    }

    fun findById(id: Int): User? =
        userRepository.findById(id)

    fun findByEmail(email: String): User? =
        userRepository.findByEmail(email)

    fun findAll(): List<User> =
        userRepository.findAll()
            .toList()

    fun deleteById(id: Int): Int =
        userRepository.deleteByID(id)

    fun updateUser(user : User) : Int = userRepository.updateUser(user)
}