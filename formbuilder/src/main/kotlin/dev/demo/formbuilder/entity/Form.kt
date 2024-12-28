package com.example.formbuilder.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "forms")
class Form(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,

    var published: Boolean = false,

    var submitUrl: String? = null,

    var submitMethod: String? = null,

    @OneToMany(mappedBy = "form", cascade = [CascadeType.ALL], orphanRemoval = true)
    var fields: MutableList<Field> = mutableListOf()
)
