package com.naw.image_ine.domain

import kotlin.random.Random

class ImageIneRandomGenerator(): RandomGenerator {
    override suspend fun getRandom(listSize: Int) =
        Random.nextInt(listSize - 1)
}