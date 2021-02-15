package com.picpay.desafio.android.contacts.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.doAnswer
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

class ContactsRepositoryTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var serviceMock: PicPayService

    @Mock
    lateinit var userDaoMock: UserDao

    private val repository: ContactsRepository by lazy {
        ContactsRepository(service = serviceMock, userDao = userDaoMock)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testEmissionByUserDaoLiveData() {
        //given
        val userExample = User(
            "",
            "Filipi",
            1,
            "@filipi"
        )
        val getAllLiveData = MutableLiveData<List<User>>()
        Mockito.`when`(userDaoMock.getAll()).thenReturn(getAllLiveData)
        //when
        val liveData = repository.getContactsLiveData()
        getAllLiveData.postValue(listOf(userExample))
        //then
        assertEquals(listOf(userExample), liveData.value)
    }

    @Test
    fun testReceivedDataFromServiceWithSuccess() {
        runBlockingTest {
            //given
            val userExample = User(
                "",
                "Filipi",
                1,
                "@filipi"
            )
            Mockito.`when`(serviceMock.getUsers())
                .thenReturn(listOf(userExample))
            //when
            val resultWrapper = repository.updateCachedValues()
            //then
            assertEquals(ResultWrapper.Success::class, resultWrapper::class)
            assertEquals(userExample, resultWrapper.successValue?.get(0))
            Mockito.verify(userDaoMock).insertAll(listOf(userExample))
        }
    }

    @Test
    fun testExceptionWhenCallService() {
        runBlockingTest {
            //given
            val exception = Exception("error!!")
            Mockito.`when`(serviceMock.getUsers()).doAnswer { throw exception }
            //when
            val resultWrapper = repository.updateCachedValues()
            //then
            assertEquals(ResultWrapper.Error::class, resultWrapper::class)
            assertEquals(exception, resultWrapper.errorValue)
            Mockito.verifyNoInteractions(userDaoMock)
        }
    }
}