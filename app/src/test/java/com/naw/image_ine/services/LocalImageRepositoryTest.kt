package com.naw.image_ine.services

import android.content.Context
import com.naw.image_ine.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * TODO:
 * Class is currently not testable because of it's dependency on File.class
 * Refactor to have a File provider.
 */
@ExperimentalCoroutinesApi
class LocalImageRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private var context: Context = mock(Context::class.java)

    private lateinit var repo: LocalImageRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repo = LocalImageRepository(context)
    }

    @After
    fun tearDown() {
    }
}