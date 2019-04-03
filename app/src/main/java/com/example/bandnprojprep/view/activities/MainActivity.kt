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
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        rv_bookView.layoutManager = linearLayoutManager

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
                    val itemsList: List<Items> = response.body()!!.items
                    Log.d("MainActivity", "" + itemsList.size)
                    //to be taken out after debugging
                    var msg: String = ""
                    for (item: Items in itemsList.iterator()) {
                        msg = msg + item.volumeInfo.title + "\n"
                    }
                    //see above-----------------------
                    adapter = RvAdapter(itemsList)
                    rv_bookView.adapter = adapter
                    //to be taken out after debugging
                    Toast.makeText(this@MainActivity, "List of Titles  \n  " + msg, Toast.LENGTH_LONG).show()
                    //see above-----------------------
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