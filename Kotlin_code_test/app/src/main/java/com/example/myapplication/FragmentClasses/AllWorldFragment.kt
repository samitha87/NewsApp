package com.example.myapplication.FragmentClasses


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

class AllWorldFragment : Fragment() {

    val dataSource = ArrayList<newsItemObject> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val userPrefs = arrayOf("Bitcoin","Apple","Earthquake","Animal")
        val adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item, // Layout
            userPrefs
        )

        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        selection_spinner.adapter = adapter;
        filterLayout.visibility = View.VISIBLE
        news_list_view.visibility = View.VISIBLE

        // Set an on item selected listener for spinner object
        selection_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                getPrefNews(userPrefs.get(position), "1")
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
                getPrefNews(userPrefs.get(0), "0")
            }
        }
// listview item click event
        news_list_view.setOnItemClickListener { parent, view, position, id ->


            var newsTitle       = dataSource.get(position).title
            var newsDescription = dataSource.get(position).description
            var newsImage_url   = dataSource.get(position).newsImageUrl
            var newsOriginUrl   = dataSource.get(position).newsUrl
            var newsContent     = dataSource.get(position).Contents

            val intent = Intent(context, detal_view::class.java)
            intent.putExtra("nTitle", newsTitle)
            intent.putExtra("nDescription",newsDescription)
            intent.putExtra("nImageUrl", newsImage_url)
            intent.putExtra("mOriginUrl", newsOriginUrl)
            intent.putExtra("nContents", newsContent)
            startActivity(intent)
        }
    }

    fun getPrefNews(pref: String, update: String){

        try {/*initiate request queue*/
            var queue = Volley.newRequestQueue(context)
            var reqUrl : String = APIEndPoints.PRIMARY_API + APIEndPoints.get_news + pref+ "&apiKey=" + AppConstants.API_key + "&sortBy=popularity"

            // Request a string response from the provided URL.
            val stringReq = StringRequest(
                Request.Method.GET, reqUrl,
                Response.Listener<String> { response ->

                    var strResp = response.toString()

                    var responseObj = JSONObject(strResp)

                    if (dataSource.size>0)
                        dataSource.clear()

                    var articles = responseObj.getJSONArray("articles")

                    for (i in 0..articles!!.length() - 1) {

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

                },
                Response.ErrorListener {
                    //                textView!!.text = "That didn't work!"
                })

            queue.add(stringReq)
        } catch (e: Exception) {
            Log.e("error",e.message)
        }
    }
}
