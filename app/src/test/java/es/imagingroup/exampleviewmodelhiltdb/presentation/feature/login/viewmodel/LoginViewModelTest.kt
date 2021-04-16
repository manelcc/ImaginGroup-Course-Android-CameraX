package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.work.impl.utils.LiveDataUtils
import app.cash.turbine.test
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import es.imagingroup.exampleviewmodelhiltdb.domain.usecase.GetLoginUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import utils.*
import utils.getOrAwaitValue
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import org.mockito.Mockito.`when`as whenever

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()




    @Mock
    lateinit var useCase: GetLoginUseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(useCase)

    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun getUserName() = coroutineScope.runBlockingTest {
        val mockFlow = flow {
            delay(10000)
            emit(User("manel","cabezas"))
        }

        whenever(useCase.logIn("uno","dos")).thenReturn(mockFlow)

        viewModel.logIn("uno","dos")

        val actual = useCase.logIn("uno", "dos")

        coroutineScope.pauseDispatcher()
        actual.onStart {
            viewModel.loading.getOrAwaitValue() assertValue true
        }.collect()

        advanceTimeBy(3000)
        advanceUntilIdle()



        advanceTimeBy(8000)
        advanceUntilIdle()

        actual.onCompletion {
            viewModel.loading.getOrAwaitValue() assertValue false
        }.collect {
            val user= viewModel.user.getOrAwaitValue()
            user.name assertValue "jose"
        }




        coroutineScope.cleanupTestCoroutines()



    }

}