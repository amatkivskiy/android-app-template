package com.github.amatkivskiy.template

import com.github.amatkivskiy.template.domain.PostExecutionThread
import com.github.amatkivskiy.template.domain.ThreadExecutor
import com.github.amatkivskiy.template.domain.usecase.UseCase
import com.github.kittinunf.result.Result
import com.nhaarman.mockito_kotlin.doAnswer
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.amshove.kluent.`should be`
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class UseCaseTests {
    private lateinit var useCase: TestUseCase
    private val testScheduler = TestScheduler()

    @Mock
    private val mockThreadExecutor: ThreadExecutor = mock()
    @Mock
    private val mockPostExecutionThread: PostExecutionThread = mock()

    @Before
    fun setUp() {
        // Setup schedulers to run in the same thread where tests are run.
        `when`(mockThreadExecutor.execute(any(Runnable::class))).doAnswer {
            val arg = it.arguments[0] as Runnable
            testScheduler.scheduleDirect(arg)
            return@doAnswer null
        }

        given(mockPostExecutionThread.scheduler).willReturn(testScheduler)

        useCase = TestUseCase(mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun `get raw observable completes with success`() {
        useCase.getRawObservable()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete()
                .values()[0]
                .get()
                .`should be`(1)
    }

    @Test
    fun `get raw configured observable completes with success`() {
        val observer = useCase.getConfiguredObservable()
                .test()

        // Trigger all scheduled actions.
        testScheduler.triggerActions()

        observer.await()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete()

        // Verify that executors threads were used  when configuring observable schedulers
        verify(mockPostExecutionThread, times(1)).scheduler
        verify(mockThreadExecutor, times(1)).execute(any(Runnable::class))
    }

    private class TestUseCase constructor(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread)
        : UseCase<Int, Exception>(threadExecutor, postExecutionThread) {

        override fun getRawObservable(): Observable<Result<Int, Exception>> {
            return Observable.just(Result.of { 1 })
        }
    }
}