package com.example.myapplication.FragmentClasses


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.PreferenceManager.SharedPreference

import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_top_news.*

class profileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        news_list_view.visibility = View.GONE
        profile_layout.visibility = View.VISIBLE

        saveButton.setOnClickListener {

            var isOk: Boolean = true;

            if (userNameText.length() == 0) {
                userNameText.error = "Invalid user name."
                isOk = false
            }

            if (passwordText.length() < 8) {
                passwordText.error = "Invalid password."
                isOk = false
            }

            if (emailText.length() == 0){
                emailText.error = "Email is required."
                isOk = false
            }

            if (isOk){
                val sharedPreference:SharedPreference=SharedPreference(context!!)

                sharedPreference.save("UserName", userNameText.text.toString())
                sharedPreference.save("passkey", passwordText.text.toString())
                sharedPreference.save("email", emailText.text.toString())

            }


        }





    }




}
