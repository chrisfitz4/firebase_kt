package com.illicitintelligence.android.firebasekotlin.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.illicitintelligence.android.firebasekotlin.model.Message
import com.illicitintelligence.android.firebasekotlin.util.Constants
import java.lang.Exception

class MyViewModel(application: Application): AndroidViewModel(application) {

    var reference: DatabaseReference
    var mutableLiveData = MutableLiveData<ArrayList<Message>>()

    init{
        reference = FirebaseDatabase.getInstance().getReference(Constants.PATH)
        val eventListener : ValueEventListener = object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG_X","onCancelled: "+p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                val list = /*mutableLiveData.value?:*/ ArrayList<Message>()
                p0.children.map{
                    if (it.getValue(Message::class.java) != null) {
                        list.add(it.getValue(Message::class.java)!!)
                    }
                }
                Log.d("TAG_X","onDataChange: "+list.size+" "+list[list.size-1])
                mutableLiveData.setValue(list)

            }
        }
        reference.addValueEventListener(eventListener)
    }


    fun sendRealMessage(message: Message) {
        val childKey = reference.push().getKey()
        if (childKey != null) {
            reference.child(childKey).setValue(message)
        }
    }
}