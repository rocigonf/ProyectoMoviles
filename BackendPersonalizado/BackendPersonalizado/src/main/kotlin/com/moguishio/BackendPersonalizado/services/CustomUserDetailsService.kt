package com.moguishio.BackendPersonalizado.services

import com.moguishio.BackendPersonalizado.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.moguishio.BackendPersonalizado.model.User

@Service
class CustomUserDetailsService(
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    userRepository.findByEmail(username)
      ?.mapToUserDetails()
      ?: throw UsernameNotFoundException("Not found!")

  private fun ApplicationUser.mapToUserDetails(): UserDetails =
    User.builder()
      .username(this.email)
      .password(this.password)
      .roles(if (this.roleId == 1) "USER" else if (this.roleId == 2) "ADMIN" else "")
      .build()
}