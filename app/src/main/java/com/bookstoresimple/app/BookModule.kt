package com.bookstoresimple.app

import androidx.room.Room
import com.bookstoresimple.app.cache.BookCacheImpl
import com.bookstoresimple.app.cache.db.BookDatabase
import com.bookstoresimple.app.cache.preference.BookPreference
import com.bookstoresimple.app.data.BookDataRepository
import com.bookstoresimple.app.data.executor.JobExecutor
import com.bookstoresimple.app.data.repository.BookCache
import com.bookstoresimple.app.data.repository.BookRemote
import com.bookstoresimple.app.data.source.BookCacheDataStore
import com.bookstoresimple.app.data.source.BookDataStoreFactory
import com.bookstoresimple.app.data.source.BookRemoteDataStore
import com.bookstoresimple.app.domain.executor.PostExecutionThread
import com.bookstoresimple.app.domain.executor.ThreadExecutor
import com.bookstoresimple.app.domain.repository.BookRepository
import com.bookstoresimple.app.presentation.viewmodel.BookViewModel
import com.bookstoresimple.app.remote.BookRemoteImpl
import com.bookstoresimple.app.remote.service.BookServiceFactory
import com.bookstoresimple.app.ui.UiThread
import com.bookstoresimple.app.usecase.GetBookListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

}

val cacheModule = module {

    single { Room.databaseBuilder(
        androidContext(),
        BookDatabase::class.java,
        "book.db"
    ).build() }

    factory { get<BookDatabase>().bookDao() }

    single { BookPreference(get()) }

    single<BookCache> { BookCacheImpl(get(), get()) }
}

val dataModule = module {

    factory { BookCacheDataStore(get()) }

    factory { BookRemoteDataStore(get()) }

    factory { BookDataStoreFactory(get(), get(), get()) }

    factory<BookRepository> { BookDataRepository(get()) }
}

val domainModule = module {

}

val presentationModule = module {

    viewModel { BookViewModel(get()) }
}

val remoteModule = module {

    single { BookServiceFactory.makeBookService(BuildConfig.DEBUG) }

    single<BookRemote> { BookRemoteImpl(get()) }
}

val useCaseModule = module {

    single { GetBookListUseCase(get(), get(), get()) }
}

val mobileUiModule = module {

    single { JobExecutor() as ThreadExecutor }

    single { UiThread() as PostExecutionThread }
}