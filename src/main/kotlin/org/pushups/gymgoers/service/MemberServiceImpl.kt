package org.pushups.gymgoers.service

import jakarta.persistence.EntityNotFoundException
import org.pushups.gymgoers.dto.AddMemberRequest
import org.pushups.gymgoers.dto.MemberDto
import org.pushups.gymgoers.dto.UpdateMemberRequest
import org.pushups.gymgoers.dto.toDto
import org.pushups.gymgoers.exception.ResourceNotFoundException
import org.pushups.gymgoers.model.Member
import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val repository: MemberRepository
) : MemberService {
    override fun getAll(pageable: Pageable): Page<MemberDto> =
        repository.findAll(pageable).map { it.toDto() }

    override fun findAllActive(pageable: Pageable): Page<MemberDto> =
        repository.findByActiveTrue(pageable).map { it.toDto() }

    override fun addMember(req: AddMemberRequest): MemberDto {
        val member = Member(
            firstName = req.firstName,
            lastName = req.lastName,
            email = req.email,
            phoneNumber = req.phoneNumber
        )
        return repository.save(member).toDto()
    }

    override fun deleteById(id: Long) {
        if (!repository.existsById(id)){
            throw ResourceNotFoundException("Member with id: $id not found")
        }
        repository.deleteById(id)
    }

    override fun findByName(name: String, pageable: Pageable): Page<MemberDto> =
        repository.findByName(name, pageable).map { it.toDto() }

    override fun updateMember(req: UpdateMemberRequest, id: Long): MemberDto {
        val member = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Member with id: $id not found") }
        member.firstName = req.firstName
        member.lastName = req.lastName
        member.email = req.email
        member.phoneNumber = req.phoneNumber

        return repository.save(member).toDto()
    }

    override fun toggleActive(id: Long): MemberDto {
        val member = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("Member not found") }
        member.active = !member.active
        return repository.save(member).toDto()
    }

    override fun toggleRole(id: Long): MemberDto {
        val member = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Member not found") }
        member.role = if (member.role == "ADMIN") "MEMBER" else "ADMIN"
        return repository.save(member).toDto()
    }
}