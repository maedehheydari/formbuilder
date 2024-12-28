package com.example.formbuilder.dto

data class FieldDto(
    var id: Long? = null,
    var name: String? = null,
    var label: String? = null,
    var type: String? = null,         // TEXT, NUMBER, BOOLEAN, DATE
    var defaultValue: String? = null
)
