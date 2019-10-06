package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.AppData.APIEndPoints
import com.example.myapplication.AppData.AppConstants
import com.example.myapplication.FragmentClasses.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getBitcoinNews()

        val mNewsViewPagerAdapter  = NewsViewPagerAdapter(supportFragmentManager)
        mNewsViewPagerAdapter.addFragment(TopNewsFragment(), "Top News")
        mNewsViewPagerAdapter.addFragment(AllWorldFragment(), "All Worls")
        mNewsViewPagerAdapter.addFragment(profileFragment(), "Profile")
        viewPager.adapter = mNewsViewPagerAdapter
        tabs.setupWithViewPager(viewPager)

    }

    /*
    * get news for initiate views
    * */

    fun getBitcoinNews(){

        /*initiate request queue*/
        var queue = Volley.newRequestQueue(this)
        var reqUrl : String = APIEndPoints.PRIMARY_API +APIEndPoints.get_news + "bitcoin&apiKey=" + AppConstants.API_key

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, reqUrl,
            Response.Listener<String> { response ->

                var strResp = response.toString()

                Log.i("response", strResp)

            },
            Response.ErrorListener {
//                textView!!.text = "That didn't work!"
            })

        queue.add(stringReq)
    }



    class NewsViewPagerAdapter (manager: FragmentManager) : FragmentPagerAdapter(manager){

        //create fragment list
        private val fragmentList :MutableList<Fragment> = ArrayList()
        //create fragment title list
        private val titlesList :MutableList<String>     = ArrayList()



        override fun getItem(position: Int): Fragment {
            //return fragments
            return fragmentList[position]
        }

        override fun getCount(): Int {
//            return count of fragments
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titlesList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titlesList[position]
        }

    }

}
