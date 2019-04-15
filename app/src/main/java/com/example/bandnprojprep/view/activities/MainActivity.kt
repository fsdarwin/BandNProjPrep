package com.example.bandnprojprep.view.activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.bandnprojprep.utils.ApiInterface
import retrofit2.Call
import BooksInfo
import Items
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import com.example.bandnprojprep.R
import com.example.bandnprojprep.view.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val TAG: String = "FRANK "
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapter
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        rv_bookView.layoutManager = linearLayoutManager

        // OnClick Listener
        this.btn_search.setOnClickListener(View.OnClickListener {
            //Set the query string
            query = et_search_terms.text.toString()
            // Call the API service
            callWebService()
        })
    }

    private fun callWebService() {
        //Create API service object
        val apiService = ApiInterface.create()
        // Make the call using the input query string
        val call = apiService.getBooksInfo(query)
        // Make the async call
        call.enqueue(object : Callback<BooksInfo> {
            override fun onResponse(call: Call<BooksInfo>, response: retrofit2.Response<BooksInfo>?) {
                if (response != null) {
                    // Pull the data from the response
                    val itemsList: List<Items> = response.body()!!.items
                    // Finish setting up the recyclerView
                    adapter = RvAdapter(itemsList)
                    rv_bookView.adapter = adapter
                }

            }

            override fun onFailure(call: Call<BooksInfo>, t: Throwable) {
                Log.e(
                    TAG, t.toString()
                )
            }
        })
    }
}