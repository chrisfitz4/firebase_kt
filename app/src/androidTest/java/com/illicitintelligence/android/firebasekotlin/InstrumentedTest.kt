package com.illicitintelligence.android.firebasekotlin

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.illicitintelligence.android.firebasekotlin.view.MainActivity
import org.junit.Rule
import org.junit.Test

class InstrumentedTest {


    @Rule
    var testRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testSendMessage(){
        Espresso.onView(withId(R.id.new_message_et)).perform(typeText("hello, how's it going?"))
        Espresso.onView(withId(R.id.send_bt)).perform(click())
        Espresso.onView(withId(R.id.swap_bt)).perform(click())
        Espresso.onView(withId(R.id.new_message_et)).perform(typeText("I'm good, how are you?"))
        Espresso.onView(withId(R.id.send_bt)).perform(click())
    }

}