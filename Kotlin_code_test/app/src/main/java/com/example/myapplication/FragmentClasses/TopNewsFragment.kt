package com.example.myapplication.FragmentClasses


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.AppAdapters.newsItemAdapter
import com.example.myapplication.AppData.APIEndPoints
import com.example.myapplication.AppData.AppConstants
import com.example.myapplication.ObjectData.newsItemObject

import com.example.myapplication.R
import com.example.myapplication.detal_view
import kotlinx.android.synthetic.main.fragment_top_news.*
import org.json.JSONObject

class TopNewsFragment : Fragment() {

    private val dataSource = ArrayList<newsItemObject> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getBitcoinNews()

        news_list_view.setOnItemClickListener { parent, view, position, id ->

            var newsTitle       = dataSource.get(position).title
            var newsDescription = dataSource.get(position).description
            var newsimageUrl   = dataSource.get(position).newsImageUrl
            var newsOriginUrl   = dataSource.get(position).newsUrl
            var newsContent     = dataSource.get(position).Contents

            val intent = Intent(context, detal_view::class.java)
            intent.putExtra("nTitle", newsTitle)
            intent.putExtra("nDescription",newsDescription)
            intent.putExtra("nImageUrl", newsimageUrl)
            intent.putExtra("mOriginUrl", newsOriginUrl)
            intent.putExtra("nContents", newsContent)
            startActivity(intent)
        }
    }


    private fun getBitcoinNews(){

        try {/*initiate request queue*/
            var queue = Volley.newRequestQueue(context)
            var reqUrl : String = APIEndPoints.PRIMARY_API + APIEndPoints.get_top_news + "&apiKey=" + AppConstants.API_key + "&pageSize=100"

            // Request a string response from the provided URL.
            val stringReq = StringRequest(
                Request.Method.GET, reqUrl,
                Response.Listener<String> { response ->

                    var strResp = response.toString()

                    var responseObj = JSONObject(strResp)
                    var articles = responseObj.getJSONArray("articles")

                    for (i in 0 until articles.length()) {

                        var singleObject = articles.getJSONObject(i)
                        var singleNewsItem = newsItemObject()
                        singleNewsItem.title = singleObject.getString("title")
                        singleNewsItem.description = singleObject.getString("description")
                        singleNewsItem.newsImageUrl = singleObject.getString("urlToImage")
                        singleNewsItem.newsUrl = singleObject.getString("url")
                        singleNewsItem.Contents = singleObject.getString("content")
                        singleNewsItem.publishedAt = singleObject.getString("publishedAt")

                        dataSource.add(singleNewsItem)
                    }

                    var adapter = newsItemAdapter(context!!, dataSource)
                    news_list_view.adapter = adapter
                    news_list_view.visibility = View.VISIBLE

                },
                Response.ErrorListener {

                })

            queue.add(stringReq)

        } catch (e: Exception) {
            Log.e("error",e.message)
        }
    }


}
