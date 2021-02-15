package com.picpay.desafio.android.contacts.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.picpay.desafio.android.contacts.data.User
import com.picpay.desafio.android.network.ResultWrapper
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ContactsUseCaseTest  : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repositoryMock: IContactsRepository

    private val useCase: ContactsUseCase by lazy {
        ContactsUseCase(repository = repositoryMock)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmissionByRepositoryLiveDataAndSortedValues() {
        //given
        val user1 = User(
            "",
            "Zezinho",
            1,
            "@zezinho"
        )
        val user2 = User(
            "",
            "Filipi",
            2,
            "@filipi"
        )
        val mutableLiveData = MutableLiveData<List<User>>()
        Mockito.`when`(repositoryMock.getContactsLiveData()).thenReturn(mutableLiveData)
        //when
        val liveData = useCase.getContactsLiveData()
        mutableLiveData.postValue(listOf(user1, user2))
        //then
        liveData.observeForever(Observer {
            assertEquals(listOf(user2, user1), it)
        })
    }

    @Test
    fun testUpdateCachedValuesFromRepository() {
        runBlockingTest {
            //given
            val user1 = User(
                "",
                "Zezinho",
                1,
                "@zezinho"
            )
            val resultWrapper = ResultWrapper.Success<List<User>, Exception>(listOf(user1))
            Mockito.`when`(repositoryMock.updateCachedValues())
                .thenReturn(resultWrapper)
            //when
            val result = useCase.updateCachedValues()
            //then
            assertEquals(resultWrapper, result)
        }
    }
}