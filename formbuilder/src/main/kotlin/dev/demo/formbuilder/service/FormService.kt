package com.example.formbuilder.service

import com.example.formbuilder.dto.FieldDto
import com.example.formbuilder.dto.FormDto
import com.example.formbuilder.entity.Field
import com.example.formbuilder.entity.FieldType
import com.example.formbuilder.entity.Form
import com.example.formbuilder.exception.ResourceNotFoundException
import com.example.formbuilder.repository.FormRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FormService(
    private val formRepository: FormRepository
) {

    fun createForm(formDto: FormDto): FormDto {
        val form = Form().apply {
            name = formDto.name
            published = formDto.published
            submitUrl = formDto.submitUrl
            submitMethod = formDto.submitMethod
        }

        // Convert DTO fields to entity
        formDto.fields.forEach { fieldDto ->
            val field = Field(
                name = fieldDto.name,
                label = fieldDto.label,
                type = FieldType.valueOf(fieldDto.type ?: "TEXT"), // Fallback if null
                defaultValue = fieldDto.defaultValue,
                form = form
            )
            form.fields.add(field)
        }

        val savedForm = formRepository.save(form)
        return mapToDto(savedForm)
    }

    @Transactional(readOnly = true)
    fun getAllForms(): List<FormDto> {
        return formRepository.findAll().map { mapToDto(it) }
    }

    @Transactional(readOnly = true)
    fun getForm(id: Long): FormDto {
        val form = formRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $id") }
        return mapToDto(form)
    }

    fun updateForm(id: Long, formDto: FormDto): FormDto {
        val form = formRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $id") }

        form.name = formDto.name
        form.published = formDto.published
        form.submitUrl = formDto.submitUrl
        form.submitMethod = formDto.submitMethod
        // Fields can be updated separately via /forms/{id}/fields

        val updatedForm = formRepository.save(form)
        return mapToDto(updatedForm)
    }

    fun deleteForm(id: Long) {
        val form = formRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $id") }
        formRepository.delete(form)
    }

    fun togglePublication(id: Long): FormDto {
        val form = formRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $id") }
        form.published = !form.published  // Toggle the current state
        val savedForm = formRepository.save(form)
        return mapToDto(savedForm)
    }

    @Transactional(readOnly = true)
    fun getPublishedForms(): List<FormDto> {
        return formRepository.findByPublishedTrue().map { mapToDto(it) }
    }

    @Transactional(readOnly = true)
    fun getFields(formId: Long): List<FieldDto> {
        val form = formRepository.findById(formId)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $formId") }
        return form.fields.map { mapFieldToDto(it) }
    }

    fun updateFields(formId: Long, fieldDtos: List<FieldDto>): List<FieldDto> {
        val form = formRepository.findById(formId)
            .orElseThrow { ResourceNotFoundException("Form not found with id: $formId") }

        // Clear existing fields and replace with new ones
        form.fields.clear()
        fieldDtos.forEach { fieldDto ->
            val field = Field(
                name = fieldDto.name,
                label = fieldDto.label,
                type = FieldType.valueOf(fieldDto.type ?: "TEXT"),
                defaultValue = fieldDto.defaultValue,
                form = form
            )
            form.fields.add(field)
        }

        formRepository.save(form)
        return form.fields.map { mapFieldToDto(it) }
    }

    // ---------------------------
    // MAPPING HELPERS
    // ---------------------------
    private fun mapToDto(form: Form): FormDto {
        return FormDto(
            id = form.id,
            name = form.name,
            published = form.published,
            submitUrl = form.submitUrl,
            submitMethod = form.submitMethod,
            fields = form.fields.map { mapFieldToDto(it) }
        )
    }

    private fun mapFieldToDto(field: Field): FieldDto {
        return FieldDto(
            id = field.id,
            name = field.name,
            label = field.label,
            type = field.type?.name,
            defaultValue = field.defaultValue
        )
    }
}
