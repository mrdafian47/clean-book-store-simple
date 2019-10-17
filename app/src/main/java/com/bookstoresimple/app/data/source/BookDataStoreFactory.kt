package com.bookstoresimple.app.data.source

import com.bookstoresimple.app.data.repository.BookCache
import com.bookstoresimple.app.data.repository.BookDataStore

open class BookDataStoreFactory(
    private val bookCache: BookCache,
    private val bookCacheDataStore: BookCacheDataStore,
    private val bookRemoteDataStore: BookRemoteDataStore
) {

    open fun retrieveDataStore(isCached: Boolean): BookDataStore {
        if (isCached && !bookCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    open fun retrieveCacheDataStore(): BookDataStore {
        return bookCacheDataStore
    }

    open fun retrieveRemoteDataStore(): BookDataStore {
        return bookRemoteDataStore
    }
}