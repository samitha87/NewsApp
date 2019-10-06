package com.example.myapplication.AppAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.ObjectData.newsItemObject
import com.example.myapplication.R
import kotlinx.android.synthetic.main.new_list_item.view.*

class newsItemAdapter (private val context: Context,
                       private val dataSource: ArrayList<newsItemObject>) : BaseAdapter() {

    @SuppressLint("ServiceCast")
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView = inflater.inflate(R.layout.new_list_item, parent, false)

        var url = dataSource.get(position).newsImageUrl

        rowView.news_title.text = dataSource.get(position).title

        Glide.with(context)
            .load(url)
            .into(rowView.news_image)

        return rowView

    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getCount(): Int {
       return dataSource.size
    }


}