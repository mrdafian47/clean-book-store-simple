package com.bookstoresimple.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseView {

    abstract fun getLayoutView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())
        initView(savedInstanceState)
        initEvent()
        observeData()
        loadingData()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initEvent() {

    }

    override fun observeData() {

    }

    override fun loadingData() {

    }

    override fun startLoading() {

    }

    override fun stopLoading() {

    }
}