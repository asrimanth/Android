package com.example.srimanth.kotlinfun

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var count=0

    fun reset(view: View)
    {
        count=0
        //Another method of setting text (Only in Kotlin)
        textView.text=count.toString()
    }

    fun plusOne(view : View)
    {
        count++
        textView.setText(count.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView=findViewById<TextView>(R.id.textView)
        //textView.text="Hello Dude!"
    }
}
