package com.pedrozanon.helpdesk.domain

import com.pedrozanon.helpdesk.domain.enum.StatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class ChangeStatus(
    @Id
    val id: String,

    @DBRef(lazy = true)
    val ticket: Ticket,

    @DBRef(lazy = true)
    val user: User,

    val dateChangeStatus: Date,

    val statusEnum: StatusEnum
)