package com.example.mvicore_test

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item.view.*

class InfoItem(val info: String): Item<ViewHolder>() {
    override fun getLayout(): Int = R.layout.item

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item.text = info
    }
}