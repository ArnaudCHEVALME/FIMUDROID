package com.example.fimudroid.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.adapter.ItemArtistAdapter
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.data.DataSource

class ArtistListFragment : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
        setContentView(R.layout.activity_main)



        // Initialize data.
        val myDataset = DataSource().LoadArtists()

        Log.d("TAG", myDataset.toString())



        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        Log.d("TAG", "recyclerView.toString()")

        recyclerView.adapter = ItemArtistAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }
}