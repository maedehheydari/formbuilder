package com.example.formbuilder.repository

import com.example.formbuilder.entity.Field
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FieldRepository : JpaRepository<Field, Long>
