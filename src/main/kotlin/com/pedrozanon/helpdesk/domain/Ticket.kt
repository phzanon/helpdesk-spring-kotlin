package com.pedrozanon.helpdesk.domain

import com.pedrozanon.helpdesk.domain.enum.PriorityEnum
import com.pedrozanon.helpdesk.domain.enum.StatusEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Ticket(
    @Id
    val id: String,

    @DBRef(lazy = true)
    val user: User,

    val date: Date,

    val title: String,

    val number: Long,

    val statusEnum: StatusEnum,

    val priorityEnum: PriorityEnum,

    @DBRef(lazy = true)
    val assignedUser: User,

    val description: String,

    val image: String,

    @Transient
    val changes: List<ChangeStatus>
)