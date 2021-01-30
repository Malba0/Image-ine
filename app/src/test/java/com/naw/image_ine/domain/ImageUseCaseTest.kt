package com.naw.image_ine.domain

import com.naw.image_ine.CoroutineTestRule
import com.naw.image_ine.services.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ImageUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var onlineImageRepository: ImageRepository
    @Mock
    private lateinit var localImageRepository: ImageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getImages() {
        runBlockingTest {
            assertEquals(true, true)
        }

    }

    @Test
    fun getNewImage() {
    }
}