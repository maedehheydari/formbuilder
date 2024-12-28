package com.example.formbuilder.dto

data class FormDto(
    var id: Long? = null,
    var name: String? = null,
    var published: Boolean = false,
    var submitUrl: String? = null,
    var submitMethod: String? = null,
    var fields: List<FieldDto> = emptyList()
)
