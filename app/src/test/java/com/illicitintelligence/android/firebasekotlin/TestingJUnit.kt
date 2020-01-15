package com.illicitintelligence.android.firebasekotlin

import android.os.Build
import com.illicitintelligence.android.firebasekotlin.model.Message
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class JUnitTest{


    lateinit var message: Message


    @Before
    fun setUp(){
        message = Message("chris", "hey there", 1579062742706)
    }

    @Test
    fun dateFormat(){
        assertEquals("chris",message.id)
        assertEquals("", message.dateToString())
    }


}