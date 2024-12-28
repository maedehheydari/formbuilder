package com.example.formbuilder.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "fields")
class Field(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    var label: String? = null,

    @Enumerated(EnumType.STRING)
    var type: FieldType? = null,

    var defaultValue: String? = null,

    @ManyToOne
    @JoinColumn(name = "form_id")
    var form: Form? = null
)
