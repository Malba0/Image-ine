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
    fun `hasManifest return false when no manifest`() {
        runBlockingTest {

            val hasManifest = onlineImageRepository.hasManifest()
            assertEquals(false, hasManifest)
        }
    }

    @Test
    fun `hasManifest return true when manifest present`() {
        runBlockingTest {
            onlineImageRepository.manifest = expectedManifest
            val hasManifest = onlineImageRepository.hasManifest()
            assertEquals(true, hasManifest)
        }
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

    @Test
    fun `getImage when empty returns null`() {
        runBlockingTest {

            val image = onlineImageRepository.getImage(1)
            assertEquals(null, image)
        }
    }

    @Test
    fun `getImage returns image`() {
        runBlockingTest {

            onlineImageRepository.manifest = ImageManifestDto(
                arrayListOf(expectedImage)
            )
            val actualImage = onlineImageRepository.getImage(0)
            assertEquals(expectedImage, actualImage)
        }
    }

    @Test
    fun `getImage returns null when index out of bounds`() {
        runBlockingTest {

            onlineImageRepository.manifest = ImageManifestDto(
                arrayListOf(expectedImage)
            )
            val actualImage = onlineImageRepository.getImage(2)
            assertEquals(null, actualImage)
        }
    }

    companion object {
        private val expectedImage = ImageDto(1, "author", "download")
        private val expectedManifest = ImageManifestDto(arrayListOf(expectedImage))
    }
}