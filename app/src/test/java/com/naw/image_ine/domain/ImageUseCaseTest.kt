package com.naw.image_ine.domain

import com.naw.image_ine.CoroutineTestRule
import com.naw.image_ine.services.ImageDto
import com.naw.image_ine.services.ImageManifestDto
import com.naw.image_ine.services.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImageUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val randomImageGenerator: RandomGenerator = MockRandomGenerator(0)

    private lateinit var imageUseCase: ImageUseCase

    private val testScope = TestCoroutineScope()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        imageUseCase = ImageUseCase(
            MockOnlineImageRepo(ONLINE_DTO),
            MockOnlineImageRepo(LOCAL_DTO),
            randomImageGenerator
        )

        imageUseCase.onlineImages = arrayListOf()
        imageUseCase.localImages = arrayListOf()
    }


    @After
    fun tearDown() {

    }

    @Test
    fun `getImages calls Repos when empty`() {
        testScope.runBlockingTest {
            imageUseCase.onlineImages = arrayListOf()
            imageUseCase.localImages = arrayListOf()
            val images = imageUseCase.getImages()
            assertEquals(1, images.size)

            assertEquals(1, imageUseCase.onlineImages.size)
            assertEquals(1, imageUseCase.localImages.size)
        }
    }

    @Test
    fun `getImages returns localImages`() {
        testScope.runBlockingTest {
            val images = imageUseCase.getImages()
            assertEquals(LOCAL_DTO.downloadUrl,images.first().downloadUrl)
        }
    }

    @Test
    fun `getNewImage init online repo when empty`() {
        testScope.runBlockingTest {
            imageUseCase.onlineImages = arrayListOf()
            val image = imageUseCase.getNewImage().let {
                ImageDto(it.id, it.author, it.downloadUrl)
            }
            assertEquals(ONLINE_DTO, image)
        }
    }

    @Test
    fun `getNewImage adds to localImages`() {
        testScope.runBlockingTest {
            imageUseCase.localImages.add(ImageBo(3, "author" ,"local"))
            val image = imageUseCase.getNewImage()
            assertEquals(2, imageUseCase.localImages.size)
        }
    }

    @Test
    fun `getNewImage removes from onlineImages`() {
        testScope.runBlockingTest {

            imageUseCase.onlineImages.add(ImageDto(3, "author" ,"online"))

            imageUseCase.getNewImage()

            assertEquals(0, imageUseCase.onlineImages.size)
            assertEquals(1, imageUseCase.localImages.size)
            assertEquals(1, imageUseCase.localImages.filter {
                it.id == 3
            }.size)
        }
    }

    @Test
    fun `saveLocalImages init local`() {
        testScope.runBlockingTest {
            imageUseCase.localImages = arrayListOf()
            imageUseCase.saveLocalImages()
            assertEquals(1, imageUseCase.localImages.size)
        }
    }

    companion object {
        private const val ID = 1
        private const val AUTHOR = "author"
        private const val DONWLOAD_URL = "local"

        private val TEST_IMAGE = ImageBo(ID, AUTHOR, DONWLOAD_URL)
        private val TEST_LIST = listOf(TEST_IMAGE)

        private val LOCAL_DTO = ImageDto(2, "author", "local")
        private val ONLINE_DTO = ImageDto(1, "author", "online")

        private val TEST_LIST_ONLINE =
            ImageManifestDto(arrayListOf(ImageDto(ID, AUTHOR, DONWLOAD_URL)))
    }

    internal class MockOnlineImageRepo(imageDto: ImageDto?): ImageRepository {
        private var manifest: ImageManifestDto = ImageManifestDto(
            imageDto?.let {
                arrayListOf(imageDto)
            } ?: arrayListOf()
        )
        override suspend fun getManifest() = manifest
        override suspend fun saveManifest(manifest: ImageManifestDto) = true
    }

    internal class MockRandomGenerator(private val num: Int): RandomGenerator {
        override suspend fun getRandom(listSize: Int) = num
    }
}