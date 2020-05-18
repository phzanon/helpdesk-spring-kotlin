package com.pedrozanon.helpdesk.service

import com.pedrozanon.helpdesk.domain.User
import com.pedrozanon.helpdesk.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){

    companion object {
        val logger = LoggerFactory.getLogger(UserService::class.java)
    }

    fun findByEmail(email: String) : User {
        val user = userRepository.findByEmail(email)
        logger.info("finding {}", user)
        return user
    }

    fun persist(user: User) : User{
        logger.info("persisting user {}", user)
        return userRepository.save(user)
    }

    fun findById(id: String) : User {
        val user = userRepository.findById(id).get()
        logger.info("finding user with id {}", id)
        return user
    }

    fun delete(id: String) {
        userRepository.delete(findById(id))
    }

    fun findAll(page: Int, count: Int) : Page<User> {
        val pageRequest = PageRequest.of(page, count)
        return userRepository.findAll(pageRequest)
    }
}