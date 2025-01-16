package com.moguishio.BackendPersonalizado.controller.user

import com.moguishio.BackendPersonalizado.model.User
import com.moguishio.BackendPersonalizado.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(
  private val userService: UserService
) {

  @PostMapping
  fun create(@RequestBody userRequest: UserRequest): UserResponse? {
    userService.createUser(userRequest.toModel())
    val createdUser = userService.findByEmail(userRequest.email)
    return createdUser?.toResponse()
  }


  @GetMapping
  fun listAll(): List<UserResponse> =
    userService.findAll()
      .map { it.toResponse() }

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Int): UserResponse =
    userService.findById(id)
      ?.toResponse()
      ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")


  @DeleteMapping("/{id}")
  fun deleteById(@PathVariable id: Int): ResponseEntity<Boolean> {
    val success = userService.deleteById(id)

    return if (success == 1)
      ResponseEntity.noContent()
        .build()
    else
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
  }

  private fun User.toResponse(): UserResponse =
    UserResponse(
      id = this.id,
      email = this.email,
    )

  private fun UserRequest.toModel(): User =
    User(
      id = 0,
      email = this.email,
      password = this.password,
      roleId = this.roleId
    )
}