package com.example.bandnprojprep.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.bandnprojprep.R
import com.squareup.picasso.Picasso
import Items
import com.example.bandnprojprep.utils.inflate

class RvAdapter(private val items: ArrayList<Items>) : RecyclerView.Adapter<RvAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
        val inflatedView = parent.inflate(R.layout.recycler_item_row, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RvAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var item: Items? = null

        fun bindItem(items: Items) {
            this.item = item
            Picasso.with(view.context).load(photo.url).into(view.itemImage)
            view.itemDate.text = photo.humanDate
            view.itemDescription.text = photo.explanation
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, PhotoActivity::class.java)
            showPhotoIntent.putExtra(PHOTO_KEY, photo)
            context.startActivity(showPhotoIntent)
        }

        companion object {
            private val PHOTO_KEY = "PHOTO"
        }
    }
}