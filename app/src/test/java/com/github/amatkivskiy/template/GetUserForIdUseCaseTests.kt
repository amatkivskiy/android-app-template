@file:Suppress("RemoveRedundantBackticks")

package com.github.amatkivskiy.template

import com.github.amatkivskiy.template.data.datasource.DefaultUserDataSource
import com.github.amatkivskiy.template.data.repository.AssertsReader
import com.github.amatkivskiy.template.data.repository.DiskUserRepository
import com.github.amatkivskiy.template.domain.usecase.GetUserForIdUseCase
import com.github.amatkivskiy.template.testutils.assertCompletedAndGetFirstValue
import com.github.amatkivskiy.template.testutils.streamFromFile
import com.github.amatkivskiy.template.util.isSuccessful
import com.github.amatkivskiy.template.util.isSuccessfulNonEmpty
import com.github.amatkivskiy.template.util.whenNotNull
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.amshove.kluent.`should not be null`
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetUserForIdUseCaseTests {
    private lateinit var getUserForIdUseCase: GetUserForIdUseCase

    private val assertReader: AssertsReader = mock()
    private val gson = Gson()

    @Before
    fun setUp() {
        val repository = DiskUserRepository(assertReader, gson)
        val dataSource = DefaultUserDataSource(repository)

        this.getUserForIdUseCase = GetUserForIdUseCase(mock(), mock(), dataSource)
    }

    @Test
    fun `get user for email returns correct user`() {
        whenever(assertReader.readDataFromAssert("users-list.json"))
            .doReturn(streamFromFile("users/get-all-users-list-success.json"))

        val userEmail = "joel.gonzalez@example.com"
        val result = getUserForIdUseCase.forUserEmail(userEmail)
            .getRawObservable()
            .assertCompletedAndGetFirstValue()

        result.isSuccessfulNonEmpty().`should be true`()
        result.get().isEmpty().`should be false`()

        val user = result.get().orNull()
        user.shouldNotBeNull()

        user.whenNotNull {
            it.name.shouldNotBeNull()
            it.name.last.shouldBeEqualTo("gonzalez")
            it.name.first.shouldBeEqualTo("joel")

            it.email.shouldBeEqualTo(userEmail)

            it.picture.shouldNotBeNull()
            it.picture.large.shouldBeEqualTo("https://randomuser.me/api/portraits/men/56.jpg")
        }
    }

    @Test
    fun `get user for missing user email returns empty result`() {
        whenever(assertReader.readDataFromAssert("users-list.json"))
            .doReturn(streamFromFile("users/get-all-users-list-success.json"))

        val result = getUserForIdUseCase.forUserEmail("user@email")
            .getRawObservable()
            .assertCompletedAndGetFirstValue()

        result.isSuccessful().`should be true`()
        result.get().isEmpty().`should be true`()
    }

    @Test
    fun `get user for user email in case of any issue returns error result`() {
        whenever(assertReader.readDataFromAssert("users-list.json"))
            .thenThrow(IOException("For some reason reading assert failed. :("))

        val result = getUserForIdUseCase.forUserEmail("any@email")
            .getRawObservable()
            .assertCompletedAndGetFirstValue()

        result.isSuccessful().`should be false`()
        result.component2().`should not be null`()
    }
}