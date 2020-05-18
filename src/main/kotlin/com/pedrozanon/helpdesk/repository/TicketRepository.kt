package com.pedrozanon.helpdesk.repository

import com.pedrozanon.helpdesk.domain.Ticket
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : MongoRepository<Ticket, String> {

    fun findByUserIdOrderByDateDesc(pages: Pageable, userId : String) : Page<Ticket>

    fun findByTitleIgnoreCaseContainingAndStatusEnumAndPriorityEnumOrderByDateDesc(
            title: String, statusEnum: String, priorityEnum: String, pages: Pageable
    ): Page<Ticket>

    fun findByTitleIgnoreCaseContainingAndStatusEnumAndPriorityEnumAndUserIdOrderByDateDesc(
            title: String, statusEnum: String, priorityEnum: String, pages: Pageable, userId: String
    ): Page<Ticket>

    fun findByTitleIgnoreCaseContainingAndStatusEnumAndPriorityEnumAndAssignedUserIdOrderByDateDesc(
            title: String, statusEnum: String, priorityEnum: String, pages: Pageable, assignedUserId: String
    ): Page<Ticket>

    fun findByNumber(number: Long, pages: Pageable) : Page<Ticket>
}