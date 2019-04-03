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
import com.example.bandnprojprep.R
import com.example.bandnprojprep.view.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val TAG: String = "FRANK "
    private var itemsList: ArrayList<Items> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        rv_bookView.layoutManager = linearLayoutManager
        adapter = RvAdapter(itemsList)
        rv_bookView.adapter = adapter

        this.btn_search.setOnClickListener(View.OnClickListener {
            displayProgressDialog()
            callWebService()
        })
    }

    private fun callWebService() {
        val apiService = ApiInterface.create()
        val call = apiService.getBooksInfo()
        Log.d(TAG, call.toString() + "")
        call.enqueue(object : Callback<BooksInfo> {
            override fun onResponse(call: Call<BooksInfo>, response: retrofit2.Response<BooksInfo>?) {
                if (response != null) {
                    if (pDialog != null && pDialog!!.isShowing()) {
                        pDialog.dismiss()
                    }
                    itemsList = response.body()!!.items
                    Log.d("MainActivity", "" + list.size)
                    var msg: String = ""
                    for (item: Items in list.iterator()) {
                        msg = msg + item.volumeInfo.title + "\n"
                    }
                    adapter = RvAdapter(itemsList)
                    rv_bookView.adapter = adapter
                    Toast.makeText(this@MainActivity, "List of Titles  \n  " + msg, Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<BooksInfo>, t: Throwable) {
                Log.e(TAG, t.toString());
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss()
                }
            }
        })
    }
    lateinit var pDialog: ProgressDialog
    private fun displayProgressDialog() {

        pDialog = ProgressDialog(this@MainActivity)
        pDialog!!.setMessage("Loading..")
        pDialog!!.setCancelable(false)
        pDialog!!.isIndeterminate = false
        pDialog!!.show()
    }
}