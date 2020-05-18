package com.pedrozanon.helpdesk

import com.pedrozanon.helpdesk.service.UserService
import org.apache.catalina.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserServiceTest (
    private val userService: UserService
){

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(UserServiceTest::class.java)
    }

    @Test
    fun findById() {
        Assertions.assertEquals(userService.findById("1"), userService.findById("1"))
    }
}