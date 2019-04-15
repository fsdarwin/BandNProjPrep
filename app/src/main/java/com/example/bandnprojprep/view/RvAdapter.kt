package com.example.bandnprojprep.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.bandnprojprep.R
import com.squareup.picasso.Picasso
import Items
import com.example.bandnprojprep.utils.inflate
import kotlinx.android.synthetic.main.rv_item.view.*

class RvAdapter(private val items: List<Items>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.ViewHolder {
        // See Extensions.kt for extension function
        val inflatedView = parent.inflate(R.layout.rv_item, false)
        return ViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: RvAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view: View = v

        fun bindItem(item: Items) {
            val itemUrl = item.volumeInfo.imageLinks.smallThumbnail
            Picasso.with(view.context).load(itemUrl).into(view.iv_bookImage)
            view.tv_title.text = item.volumeInfo.title
            // There can be more than one author for a book.
            // Thus, we need to account for a list of authors.
            // Authors is always presented as a list even if it's only one.
            var authorList: Int = item.volumeInfo.authors.size
            // Most books have one author, so don't waste time on iteration.
            var authors = ""
            if (authorList == 1) {
                authors = item.volumeInfo.authors[0]
            } else {
                for (authorName in item.volumeInfo.authors) {
                    authors += when (authorName == item.volumeInfo.authors.last()) {
                        true -> authorName
                        false -> "$authorName, "
                    }
                }
            }
            view.tv_author.text = authors
        }
    }
}