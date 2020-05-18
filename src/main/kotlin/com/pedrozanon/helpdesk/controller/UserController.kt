package com.pedrozanon.helpdesk.controller

import com.pedrozanon.helpdesk.domain.User
import com.pedrozanon.helpdesk.service.UserService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController (
    private val userService: UserService
){

    @GetMapping
    fun findById(@RequestHeader id : String) : ResponseEntity<User> {
        return ResponseEntity.ok(userService.findById(id))
    }

    @PostMapping
    fun persist(@RequestBody @Valid user: User) : ResponseEntity<User> {
        return ResponseEntity.ok(userService.persist(user))
    }

    @GetMapping("/findAll")
    fun findAll() : ResponseEntity<Page<User>>{
        return ResponseEntity.ok(userService.findAll(1, 10))
    }
}