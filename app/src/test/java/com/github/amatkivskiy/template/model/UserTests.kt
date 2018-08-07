package com.github.amatkivskiy.template.model

import com.github.amatkivskiy.template.domain.model.Name
import com.github.amatkivskiy.template.domain.model.Picture
import com.github.amatkivskiy.template.domain.model.User
import org.amshove.kluent.`should be`
import org.junit.Test

class UserTests {

    @Test
    fun `all user models constructors work fine`() {
        val name = Name("first", "second")
        val email = "email"
        val picture = Picture("image")
        val phone = "phone"
        val cell = "cell"

        User(name, email, picture, phone, cell).let {
            it.name `should be` name
            it.email `should be` email
            it.picture `should be` picture
            it.phone `should be` phone
            it.cell `should be` cell
        }
    }
}