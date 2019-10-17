package com.bookstoresimple.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bookstoresimple.app.R
import com.bookstoresimple.app.presentation.data.ResourceState
import com.bookstoresimple.app.presentation.viewmodel.BookViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<BookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.bookList.observe(this, Observer {
            when (it.status) {
                ResourceState.LOADING -> {
                    Log.d("Main", "State Loading")
                }
                ResourceState.SUCCESS -> {
                    Log.d("Main", "State Success")
                    it.data?.forEach { book ->
                        Log.d("Main", "Book ${book.title}")
                        Log.d("Main", "Book ${book.description}")
                    }
                }
                ResourceState.ERROR -> {
                    Log.d("Main", "State Error")
                }
            }
        })

        viewModel.fetchBookList()
    }
}
