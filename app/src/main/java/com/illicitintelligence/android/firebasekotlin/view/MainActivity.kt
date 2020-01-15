package com.illicitintelligence.android.firebasekotlin.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.illicitintelligence.android.firebasekotlin.R
import com.illicitintelligence.android.firebasekotlin.adapter.RVAdapter
import com.illicitintelligence.android.firebasekotlin.model.Message
import com.illicitintelligence.android.firebasekotlin.util.Constants
import com.illicitintelligence.android.firebasekotlin.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var user1: String
    lateinit var user2: String

    lateinit var adapter: RVAdapter

    lateinit var liveData: MutableLiveData<ArrayList<Message>>
    lateinit var viewModel: MyViewModel
    lateinit var observer: Observer<ArrayList<Message>>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

        getUsers()
        setClickListeners()
        var list = liveData.value ?: ArrayList<Message>()
        adapter = RVAdapter(list,user1)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
    }



    fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        liveData = viewModel.mutableLiveData
        observer = object: Observer<ArrayList<Message>>{
            override fun onChanged(t: ArrayList<Message>?) {
                adapter = RVAdapter(t?: ArrayList<Message>(),user1)
                recycler_view.adapter = adapter
                if(adapter.list.size>1) {
                    recycler_view.scrollToPosition(adapter.list.size - 1)
                }
            }
        }
        liveData.observe(this,observer)
    }

    fun setClickListeners(){
        swap_bt.setOnClickListener{
            var copy = user1
            user1 = user2
            user2 = copy
            updateUser()
            adapter = RVAdapter(liveData.value?:ArrayList<Message>(),user1)
            recycler_view.adapter = adapter
            recycler_view.scrollToPosition(adapter.itemCount-1)
        }
        send_bt.setOnClickListener{
            sendMessage()
        }
    }

    fun getUsers(){

        val sharedPreferences = getSharedPreferences(Constants.SHARED_P, Context.MODE_PRIVATE)
        val user1SP = sharedPreferences.getString(Constants.USER1,null)
        val user2SP = sharedPreferences.getString(Constants.USER2,null)
        if(user1SP==null||user2SP==null){
            //todo let the app user set the messaging users
            user1 = "hanahsloan"
            user2 = "chrisfitz"
            updateUser()
        }else{
            user1 = user1SP
            user2 = user2SP
            updateUser()
        }
    }

    fun updateUser(){
        user_tv.text = user1
        talking_to_tv.text = user2
    }

    fun sendMessage(){
        if(!new_message_et.text.equals("")){
            val message = Message(user1, new_message_et.text.toString(),System.currentTimeMillis())
            Log.d("TAG_X", message.toString())
            new_message_et.setText("")
            viewModel.sendRealMessage(message)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(send_bt.windowToken, 0)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        liveData.removeObservers(this)
    }
}
