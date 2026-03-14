package org.pushups.gymgoers.controller

import org.pushups.gymgoers.dto.AddMemberRequest
import org.pushups.gymgoers.dto.MemberDto
import org.pushups.gymgoers.dto.UpdateMemberRequest
import org.pushups.gymgoers.service.MemberService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val service: MemberService
){
    @GetMapping
    fun findAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "25") size: Int
    ): Page<MemberDto> = service.getAll(PageRequest.of(page,size))

    @GetMapping("/active")
    fun findAllActive(
        @RequestParam(defaultValue = "0") page:Int,
        @RequestParam(defaultValue = "25") size: Int
    ): Page<MemberDto> = service.findAllActive(PageRequest.of(page,size))

    @GetMapping("/search")
    fun findMember(
        @RequestParam name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "25") size: Int
    ): Page<MemberDto> = service.findByName(name, PageRequest.of(page,size))

    @PostMapping
    fun addMember(
        @RequestBody req: AddMemberRequest
    ): MemberDto = service.addMember(req)

    @PutMapping("/{id}")
    fun updateMember(
        @PathVariable id: Long,
        @RequestBody req: UpdateMemberRequest
    ): MemberDto = service.updateMember(id = id, req =  req)

    @PatchMapping("/{id}")
    fun toggleActive(
        @PathVariable id: Long
    ): MemberDto = service.toggleActive(id)

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id: Long): ResponseEntity<Void> {
        service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}