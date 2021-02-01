package com.naw.image_ine.services

import com.naw.image_ine.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class OnlineImageRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var onlineImageRepository: OnlineImageRepository

    // TODO: Refactor to provide Retrofit.Call<ImageApi> to OnlineImageRepo constructor
    // It will allow the mocking of the call.
//    private var api: ImageApi = mock(ImageApi::class.java)
//    `when`(api.fetchImages()).thenReturn(arrayOf(expectedImageDao))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        onlineImageRepository = OnlineImageRepository()
    }

    @After
    fun tearDown() {
        onlineImageRepository.manifest = null
    }

    @Test
    fun `getManifest return persistent manifest`() {
        runBlockingTest {
            onlineImageRepository.manifest = expectedManifest
            val actualManifest = onlineImageRepository.getManifest()
            assertEquals(expectedManifest, actualManifest)
        }
    }

    @Test
    fun `getManifest queries API for manifest`() {
        runBlockingTest {

            fail("Refactor in order to conduct test")
        }
    }

    companion object {
        private val expectedImage = ImageDto(1, "author", "download")
        private val expectedManifest = ImageManifestDto(arrayListOf(expectedImage))
    }
}