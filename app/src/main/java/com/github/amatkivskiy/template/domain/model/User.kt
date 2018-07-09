package com.github.amatkivskiy.template.domain.model

data class User(val name: Name, val email: String, val picture: Picture, val phone: String, val cell: String)

data class Name(val first: String, val last: String)
data class Picture(val large: String)