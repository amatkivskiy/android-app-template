package com.github.amatkivskiy.template.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") val name: Name,
    @SerializedName("email") val email: String,
    @SerializedName("picture") val picture: Picture,
    @SerializedName("phone") val phone: String,
    @SerializedName("cell") val cell: String
)

data class Name(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

data class Picture(@SerializedName("large") val large: String)