package com.bookstoresimple.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookstoresimple.app.R
import com.bookstoresimple.app.domain.model.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdapter(
    private val bookList: List<Book>
) : RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun getItemCount(): Int = bookList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(bookList[position])
    }

    inner class BookHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(book: Book) {
            with(itemView) {
                tvBookTitle?.text = book.title
                tvBookDescription?.text = book.description
            }
        }
    }
}