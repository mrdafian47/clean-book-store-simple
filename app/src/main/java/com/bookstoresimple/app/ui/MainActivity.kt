package com.bookstoresimple.app.ui

import android.os.Bundle
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

        with(rvBook) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            adapter = bookAdapter
        }
    }

    override fun observeData() {

        viewModel.bookList.observe(this, Observer { resource ->
            parseObserveData(resource,
                resultLoading = {

                },
                resultSuccess = {
                    bookList.clear()
                    bookList.addAll(it)
                    bookAdapter.notifyDataSetChanged()
                },
                resultError = {

                })
        })
    }

    override fun loadingData() {

        viewModel.fetchBookList()
    }
}
