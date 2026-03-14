package org.pushups.gymgoers.service

import org.pushups.gymgoers.dto.AddMemberRequest
import org.pushups.gymgoers.dto.MemberDto
import org.pushups.gymgoers.dto.UpdateMemberRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MemberService {
    fun getAll(pageable: Pageable): Page<MemberDto>
    fun findAllActive(pageable: Pageable): Page<MemberDto>
    fun addMember(member: AddMemberRequest): MemberDto
    fun deleteById(id: Long)
    fun findByName(name: String, pageable: Pageable): Page<MemberDto>
    fun updateMember(req: UpdateMemberRequest, id: Long): MemberDto
    fun toggleActive(id: Long): MemberDto
}