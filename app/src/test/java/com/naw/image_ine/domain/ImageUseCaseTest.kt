package com.naw.image_ine.domain

import com.naw.image_ine.CoroutineTestRule
import com.naw.image_ine.services.ImageDto
import com.naw.image_ine.services.ImageManifestDto
import com.naw.image_ine.services.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ImageUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var onlineImageRepository: ImageRepository

    @Mock
    private lateinit var localImageRepository: ImageRepository

    @Mock
    private lateinit var randomImageGenerator: RandomGenerator

    private lateinit var imageUseCase: ImageUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        imageUseCase = ImageUseCase(
            onlineImageRepository,
            localImageRepository,
            randomImageGenerator
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `On init LocalImages is empty`() {
        runBlockingTest {
            val list = imageUseCase.getImages()
            assertEquals(true, list.isEmpty())
        }
    }

    @Test
    fun getNewImage() {
        runBlockingTest {
            imageUseCase.localImages.clear()

            `when`(onlineImageRepository.getManifest()).thenReturn(
                ImageManifestDto(arrayListOf(ImageDto(1, "AUTHOR", "DONWLOAD_URL")))
            )

            `when`(randomImageGenerator.getRandom(1)).thenReturn(1)
            `when`(onlineImageRepository.getImage(1)).thenReturn(
                ImageDto(
                    ID, AUTHOR, DONWLOAD_URL
                )
            )

            val image = imageUseCase.getNewImage()

            assertEquals(1, imageUseCase.localImages.size)
        }
    }

    companion object {
        private const val ID = 1
        private const val AUTHOR = "author"
        private const val DONWLOAD_URL = "local"

        private val TEST_IMAGE = ImageBo(ID, AUTHOR, DONWLOAD_URL)
        private val TEST_LIST = listOf(TEST_IMAGE)

        private val TEST_LIST_ONLINE =
            ImageManifestDto(arrayListOf(ImageDto(ID, AUTHOR, DONWLOAD_URL)))
    }
}