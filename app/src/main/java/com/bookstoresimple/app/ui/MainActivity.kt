package com.bookstoresimple.app.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookstoresimple.app.R
import com.bookstoresimple.app.domain.model.Book
import com.bookstoresimple.app.presentation.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<BookViewModel>()

    private val bookList = mutableListOf<Book>()

    private val bookAdapter by lazy {
        BookAdapter(bookList)
    }

    override fun getLayoutView(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        Log.d("Main", "Init View")

        with(rvBook) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            adapter = bookAdapter
        }
    }

    override fun observeData() {

        Log.d("Main", "Observe Data")

        viewModel.bookList.observe(this, Observer { resource ->
            parseObserveData(resource,
                resultLoading = {
                    Log.d("Main", "State Loading ")
                },
                resultSuccess = {
                    Log.d("Main", "State Success ")
                    bookList.clear()
                    bookList.addAll(it)
                    bookAdapter.notifyDataSetChanged()
                },
                resultError = {
                    Log.d("Main", "State Error ")
                    it?.printStackTrace()
                })
        })
    }

    override fun loadingData() {

        Log.d("Main", "Loading Data")

        viewModel.fetchBookList()
    }
}
