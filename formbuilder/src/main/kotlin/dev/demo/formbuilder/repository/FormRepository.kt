package com.example.formbuilder.repository

import com.example.formbuilder.entity.Form
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormRepository : JpaRepository<Form, Long> {
    fun findByPublishedTrue(): List<Form>
}
