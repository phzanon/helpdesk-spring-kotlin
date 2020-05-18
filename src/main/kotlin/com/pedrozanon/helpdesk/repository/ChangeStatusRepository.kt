package com.pedrozanon.helpdesk.repository

import com.pedrozanon.helpdesk.domain.ChangeStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChangeStatusRepository : MongoRepository<ChangeStatus, String> {
    //fun findByTicketIdOOrderByDateChangeStatusDesc(ticket : String) : Iterable<ChangeStatus>
}