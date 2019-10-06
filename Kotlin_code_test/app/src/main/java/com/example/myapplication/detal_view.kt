package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detal_view.*

class detal_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detal_view)

        val imageUrl:String     = intent.getStringExtra("nImageUrl")
        val newsTitle:String    = intent.getStringExtra("nTitle")
        val newsDetails:String  = intent.getStringExtra("nDescription")
        val newsUrl:String      = intent.getStringExtra("mOriginUrl")


        Glide.with(this)
            .load(imageUrl)
            .into(news_detail_image_view)

        news_detail_title.text  = newsTitle
        news_detail.text        = newsDetails
        news_view_more.setOnClickListener {

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(newsUrl)
            startActivity(openURL)
        }

    }
}
