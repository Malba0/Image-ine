package com.naw.image_ine.domain

interface RandomGenerator {
    suspend fun getRandom(listSize: Int): Int
}