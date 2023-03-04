package com.example.fimudroid

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.adapter.ActuAdapter
import com.example.fimudroid.adapter.ItemArtistAdapter
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.data.DataSource
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val data = DataSource()
            data.fetchData()

            Log.i("DATA", data.getArtistes().toString())
            Log.i("DATA", data.getNews().toString())
            Log.i("DATA", data.getStands().toString())
            Log.i("DATA", data.getPays().toString())
            Log.i("DATA", data.getCategories().toString())
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_news, R.id.navigation_artiste_list
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}