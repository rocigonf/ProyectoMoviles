package com.moguishio.BackendPersonalizado.controller.user

import com.moguishio.BackendPersonalizado.model.User
import com.moguishio.BackendPersonalizado.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.Dictionary

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

  @GetMapping("/all")
  fun listAll(): List<UserResponse> =
    userService.findAll()
      .map { it.toResponse() }

  @GetMapping("/id/{id}")
  fun findById(@PathVariable id: Int): UserResponse {
    val email = SecurityContextHolder.getContext().authentication.name
    val user = userService.findByEmail(email)

    if (user?.id == id) return user.toResponse()
    else throw ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Soy una tetera.")
  }

  @GetMapping("/email/{emailInput}")
  fun findByEmail(@PathVariable emailInput : String): UserResponse {
    val email = SecurityContextHolder.getContext().authentication.name
    val user = userService.findByEmail(email)
    if (user != null && user.email == emailInput) return user.toResponse()
    else throw ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Soy una tetera.")
  }

  @DeleteMapping("/{id}")
  fun deleteById(@PathVariable id: Int): ResponseEntity<Boolean> {
    val success = userService.deleteById(id)

    return if (success == 1)
      ResponseEntity.noContent()
        .build()
    else
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
  }

  @PostMapping("/{id}")
  fun updateById(@RequestBody userRequest: UserRequest, @PathVariable id : Int): ResponseEntity<Boolean> {
    val email = SecurityContextHolder.getContext().authentication.name
    val user = userService.findByEmail(email)

    if (user?.id == id) {
      val success = userService.updateUser(userRequest.toModel())

      return if (success == 1)
        ResponseEntity.noContent()
          .build()
      else
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
    }
    else
    {
      throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
    }
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
      roleId = 1
    )
}