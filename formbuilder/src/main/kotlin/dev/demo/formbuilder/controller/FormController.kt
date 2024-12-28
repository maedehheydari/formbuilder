package com.example.formbuilder.controller

import com.example.formbuilder.dto.FieldDto
import com.example.formbuilder.dto.FormDto
import com.example.formbuilder.service.FormService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/forms")
class FormController(
    private val formService: FormService
) {

    // 1. Retrieve a list of all forms
    @GetMapping
    fun getAllForms(): List<FormDto> {
        return formService.getAllForms()
    }

    // 2. Create a new form
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createForm(@RequestBody formDto: FormDto): FormDto {
        return formService.createForm(formDto)
    }

    // 3. Retrieve a form by its specific ID
    @GetMapping("/{id}")
    fun getForm(@PathVariable id: Long): FormDto {
        return formService.getForm(id)
    }

    // 4. Update information of a specific form
    @PutMapping("/{id}")
    fun updateForm(@PathVariable id: Long, @RequestBody formDto: FormDto): FormDto {
        return formService.updateForm(id, formDto)
    }

    // 5. Delete a form
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteForm(@PathVariable id: Long) {
        formService.deleteForm(id)
    }

    // 6. View the fields of a form
    @GetMapping("/{id}/fields")
    fun getFields(@PathVariable id: Long): List<FieldDto> {
        return formService.getFields(id)
    }

    // 7. Update the fields of a form
    @PutMapping("/{id}/fields")
    fun updateFields(@PathVariable id: Long, @RequestBody fields: List<FieldDto>): List<FieldDto> {
        return formService.updateFields(id, fields)
    }

    // 8. Change the publication status of a form
    // Publish
    @PostMapping("/{id}/toggle-publication")
    fun toggleFormPublication(@PathVariable id: Long): FormDto {
        return formService.togglePublication(id)
    }

    // 9. Retrieve a list of published forms
    @GetMapping("/published")
    fun getPublishedForms(): List<FormDto> {
        return formService.getPublishedForms()
    }
}
