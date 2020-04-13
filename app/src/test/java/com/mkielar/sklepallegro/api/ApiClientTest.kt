package com.mkielar.sklepallegro.api

import com.google.gson.Gson
import com.mkielar.sklepallegro.model.OfferDTO
import com.mkielar.sklepallegro.model.OffersDTO
import com.mkielar.sklepallegro.model.PriceDTO
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


class ApiClientTest {
    private val mockWebServer = MockWebServer()

    private lateinit var apiClient: AllegroApiClient

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
        apiClient = retrofit.create(AllegroApiClient::class.java)
    }

    @Test
    fun assertProperApiObjectMapping() {
        //given
        val expected = OffersDTO(
            listOf(
                OfferDTO(
                    "1",
                    "Test Name",
                    "https://example.com/",
                    PriceDTO(
                        "100.00",
                        "PLN"
                    ),
                    "<p>Test Name</p>"
                ),
                OfferDTO(
                    "2",
                    "Test Name 2",
                    "https://example.com/2",
                    PriceDTO(
                        "1000.00",
                        "EUR"
                    ),
                    "<p>Test Name 2</p>"
                )
            )
        )

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(expected))

        mockWebServer.enqueue(response)

        //when
        val actual = apiClient.getData().blockingFirst()

        //then
        assertEquals(expected, actual)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}