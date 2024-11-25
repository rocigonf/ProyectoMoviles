package com.moguishio.moguishio.model.cats

class CatRepository {
    private val creditCardService = RetrofitInstance.catService

    suspend fun getCats(): List<Cat> {
        return creditCardService.getCats()
    }
}