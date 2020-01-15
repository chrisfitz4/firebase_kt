package com.illicitintelligence.android.firebasekotlin.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.util.*

data class Message(public var id: String = "", var content: String = "", var date: Long = 0) {




    override fun toString(): String {
        Log.d("TAG_X",""+date)
        return String.format("%s sent %s on %s",id,content,dateToString())
    }


    fun dateToString(): String{
        if(Build.VERSION.SDK_INT>25) {
            val time = java.time.Instant.ofEpochMilli(date)
            val localTime = java.time.LocalDateTime.ofInstant(time, ZoneId.systemDefault())
            val formatter = java.time.format.DateTimeFormatter.ofPattern("hh:mm")
            return localTime.format(formatter)
        }else{
            return ""
        }
    }
}