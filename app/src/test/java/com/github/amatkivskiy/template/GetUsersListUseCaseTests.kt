@file:Suppress("RemoveRedundantBackticks")

package com.github.amatkivskiy.template

import com.github.amatkivskiy.template.data.datasource.DefaultUserDataSource
import com.github.amatkivskiy.template.data.repository.AssertsReader
import com.github.amatkivskiy.template.data.repository.DiskUserRepository
import com.github.amatkivskiy.template.domain.usecase.GetUsersListUseCase
import com.github.amatkivskiy.template.testutils.assertCompletedAndGetFirstValue
import com.github.amatkivskiy.template.testutils.streamFromFile
import com.github.amatkivskiy.template.util.isSuccessful
import com.github.amatkivskiy.template.util.isSuccessfulNonEmpty
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.amshove.kluent.`should not be instance of`
import org.amshove.kluent.`should not be`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetUsersListUseCaseTests {
    private lateinit var getUsersListUseCase: GetUsersListUseCase

    private val assertReader: AssertsReader = mock()
    private val gson = Gson()

    @Before
    fun setUp() {
        val repository = DiskUserRepository(assertReader, gson)
        val dataSource = DefaultUserDataSource(repository)

        this.getUsersListUseCase = GetUsersListUseCase(null, null, dataSource)
    }

    @Test
    fun `get all beers from file returns correct list`() {
        whenever(assertReader.readDataFromAssert("users-list.json"))
            .doReturn(streamFromFile("users/get-all-users-list-success.json"))

        val result = getUsersListUseCase.getRawObservable()
            .assertCompletedAndGetFirstValue()

        result.isSuccessfulNonEmpty().`should be true`()
        result.get().size `should be equal to` 2

        val firstUser = result.get()[0]
        val secondUser = result.get()[1]

        firstUser.name `should not be` null
        firstUser.name.first `should be equal to` "joel"
        firstUser.name.last `should be equal to` "gonzalez"

        firstUser.picture `should not be` null
        firstUser.picture.large `should be equal to` "https://randomuser.me/api/portraits/men/56.jpg"

        secondUser.name `should not be` null
        secondUser.name.first `should be equal to` "valdemar"
        secondUser.name.last `should be equal to` "jørgensen"

        secondUser.picture `should not be` null
        secondUser.picture.large `should be equal to` "https://randomuser.me/api/portraits/men/42.jpg"
    }

    @Test
    fun `test get all beers on read file error returns error result`() {
        whenever(assertReader.readDataFromAssert("users-list.json"))
            .thenThrow(IOException("For some reason reading assert failed. :("))

        val result = getUsersListUseCase.getRawObservable()
            .assertCompletedAndGetFirstValue()

        result.isSuccessful().`should be false`()

        val (_, error) = result

        error `should not be instance of` IOException::class
    }
}