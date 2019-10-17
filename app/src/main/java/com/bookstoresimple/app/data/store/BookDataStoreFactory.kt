package com.bookstoresimple.app.data.store

import com.bookstoresimple.app.data.source.BookCacheDataSource

open class BookDataStoreFactory(
    private val bookCacheDataSource: BookCacheDataSource,
    private val bookCacheDataStore: BookCacheDataStore,
    private val bookRemoteDataStore: BookRemoteDataStore
) {

    open fun retrieveDataStore(isCached: Boolean): BookDataStore {
        if (isCached && !bookCacheDataSource.isExpired()) {
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