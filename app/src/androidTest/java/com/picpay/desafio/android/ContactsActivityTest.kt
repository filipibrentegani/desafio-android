package com.picpay.desafio.android

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.contacts.presentation.ContactsActivity
import com.picpay.desafio.android.contacts.presentation.ContactsViewModel
import com.picpay.desafio.android.ui.io
import com.picpay.desafio.android.ui.ui
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.wait
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import java.util.concurrent.TimeUnit


class ContactsActivityTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle() {
        launchActivity<ContactsActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)

        launchActivity<ContactsActivity>().apply {
            RecyclerViewMatchers.checkRecyclerViewItem (R.id.recyclerView, 0, withText("Ana Silva"))
            RecyclerViewMatchers.checkRecyclerViewItem (R.id.recyclerView, 1, withText("Filipi Brentegani"))
            RecyclerViewMatchers.checkRecyclerViewItem (R.id.recyclerView, 2, withText("Vanderlei Lopes"))
        }

        server.close()
    }

    companion object {
        private const val serverPort = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Filipi Brentegani\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@filipi.brentegani\"},{\"id\":1002,\"name\":\"Vanderlei Lopes\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@vanderlei.lopes\"},{\"id\":1003,\"name\":\"Ana Silva\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@ana.silva\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBodyDelay(1000, TimeUnit.MILLISECONDS)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}