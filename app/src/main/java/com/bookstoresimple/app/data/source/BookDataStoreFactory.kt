package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.data.repository.BookCache
import com.bookstoresimple.app.data.repository.BookDataSource

open class BookDataStoreFactory(
    private val bookCache: BookCache,
    private val bookCacheDataStore: BookCacheDataStore,
    private val bookRemoteDataStore: BookRemoteDataStore
) {

    open fun retrieveDataStore(isCached: Boolean): BookDataSource {
        if (isCached && !bookCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    open fun retrieveCacheDataStore(): BookDataSource {
        return bookCacheDataStore
    }

    open fun retrieveRemoteDataStore(): BookDataSource {
        return bookRemoteDataStore
    }
}