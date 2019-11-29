package com.bookstoresimple.app

import androidx.room.Room
import com.bookstoresimple.app.cache.BookCacheDataSourceImpl
import com.bookstoresimple.app.cache.db.BookDatabase
import com.bookstoresimple.app.cache.preference.BookPreference
import com.bookstoresimple.app.data.repository.BookRepositoryImpl
import com.bookstoresimple.app.data.source.BookCacheDataSource
import com.bookstoresimple.app.data.source.BookRemoteDataSource
import com.bookstoresimple.app.domain.repository.BookRepository
import com.bookstoresimple.app.presentation.viewmodel.BookViewModel
import com.bookstoresimple.app.remote.BookRemoteDataSourceImpl
import com.bookstoresimple.app.remote.service.BookServiceFactory
import com.bookstoresimple.app.usecase.GetBookListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

}

val cacheModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            BookDatabase::class.java,
            "book.db"
        ).build()
    }

    factory { get<BookDatabase>().bookDao() }

    single { BookPreference(get()) }

    factory<BookCacheDataSource> { BookCacheDataSourceImpl(get(), get()) }
}

val dataModule = module {

    factory<BookRepository> { BookRepositoryImpl(get(), get()) }
}

val domainModule = module {

}

val presentationModule = module {

    viewModel { BookViewModel(get()) }
}

val remoteModule = module {

    single { BookServiceFactory.makeBookService(BuildConfig.DEBUG) }

    factory<BookRemoteDataSource> { BookRemoteDataSourceImpl(get()) }
}

val useCaseModule = module {

    single { GetBookListUseCase(get()) }
}

val mobileUiModule = module {

}