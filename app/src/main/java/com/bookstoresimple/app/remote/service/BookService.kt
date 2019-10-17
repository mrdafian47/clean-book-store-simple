package com.bookstoresimple.app.remote.service

import com.bookstoresimple.app.domain.model.Book
import io.reactivex.Flowable
import retrofit2.http.GET

interface BookService {

    @GET("tarek360/4578e33621011e18829bad0c8d1c8cdf/raw/06d185bebc3e14a56dfa85f53288daddd4ff6a2b/books.json")
    fun getBookList(): Flowable<List<Book>>
}