package com.pedrozanon.helpdesk.repository

import com.pedrozanon.helpdesk.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findByEmail(email : String) : User
}