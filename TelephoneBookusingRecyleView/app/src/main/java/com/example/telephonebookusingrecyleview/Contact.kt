package com.example.telephonebookusingrecyleview

import java.io.Serializable
import java.util.UUID

data class Contact(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var phone: String,
    var email: String
) : Serializable
