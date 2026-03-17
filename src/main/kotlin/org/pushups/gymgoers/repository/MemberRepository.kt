package org.pushups.gymgoers.repository

import jakarta.validation.constraints.Email
import org.pushups.gymgoers.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long>{

    fun findByActiveTrue(pageable: Pageable): Page<Member>

    @Query("select * from member where " +
            "lower(first_name) like lower(concat('%', :name,'%')) or " +
            "lower(last_name) like lower(concat('%',:name,'%')) ",
        nativeQuery = true)
    fun findByName(@Param("name") name: String, pageable: Pageable): Page<Member>

    fun findByEmail(email: String): Member?
}