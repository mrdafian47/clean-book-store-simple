package com.bookstoresimple.app.cache.preference

import android.content.Context
import android.content.SharedPreferences

open class BookPreference(
    context: Context
) {

    private val bookPref: SharedPreferences

    companion object {
        private const val BOOK_PREF_NAME = "book_pref"
        private const val LAST_CACHE = "last_cache"
    }

    init {
        bookPref = context.getSharedPreferences(
            BOOK_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    var lastCacheTime
        get() = bookPref.getLong(LAST_CACHE, 0)
        set(lastCache) = bookPref.edit().putLong(LAST_CACHE, lastCache).apply()
}