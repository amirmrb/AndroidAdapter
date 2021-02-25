package com.cafebazaar.basicbadadapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val mediaAdapter = ImpairedAdapter()

    private val listOfMedias = listOf(
        Video("Video url 1"),
        Audio("Audio url 1"),
        Audio("Audio url 2"),
        Audio("Audio url 3"),
        Video("Video url 2"),
        Video("Video url 3"),
        Video("Video url 4"),
        Video("Video url 5"),
        Video("Video url 6"),
        Video("Video url 7"),
        Video("Video url 8"),
        Video("Video url 9"),
        Audio("Audio url 3"),
        Audio("Audio url 4"),
        Audio("Audio url 5"),
        Audio("Audio url 6"),
        Audio("Audio url 7"),
        Audio("Audio url 8"),
        Audio("Audio url 9"),
        Audio("Audio url 10"),
        Audio("Audio url 11"),
        Audio("Audio url 12"),
        Audio("Audio url 13"),
        Audio("Audio url 14"),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<RecyclerView>(R.id.recyclerView)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = mediaAdapter



        mediaAdapter.adapterData.addAll(listOfMedias)
    }

}