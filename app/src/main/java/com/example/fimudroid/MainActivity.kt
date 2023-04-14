package com.example.fimudroid

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //checkApiStatus()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the NavHostFragment from the layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView

        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_news, R.id.navigation_artiste_list, R.id.navigation_plan, R.id.navigation_programmation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NavigationBarView.OnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.navigation_news -> {
                    true
                }
                R.id.navigation_artiste_list -> {
                    true
                }
                R.id.navigation_plan -> {
                    true
                }
                R.id.navigation_programmation -> {
                    true
                }
                else -> false
            }
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Crédit : Samson, Réalisation : Gabin
    }

    private fun onCreateView(){
        checkApiStatus()
    }

    private fun checkApiStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val status = api.checkAPIStatus()
                if (status.error == 0) {
                    // API is up, do nothing
                } else {
                    // API is down, display a message to the user and quit the app
                    withContext(Dispatchers.Main) {
                        showApiStatusCheckErrorDialog()
                    }
                }
            } catch (e: Exception) {
                // Network error, display a message to the user and quit the app
                withContext(Dispatchers.Main) {
                    showApiStatusCheckErrorDialog()
                }
            }
        }
    }

    private fun showApiStatusCheckErrorDialog() {
        val dialogBuilder = AlertDialog.Builder(applicationContext)
        dialogBuilder.setMessage("API is down")
        dialogBuilder.setPositiveButton("OK") { _, _ ->
            exitProcess(0)
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater: MenuInflater = menuInflater
        if (navController.currentDestination?.id == R.id.navigation_faq) {
            return true
        } else {
            menuInflater.inflate(R.menu.info_menu, menu)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_faq -> {
                navController.navigate(R.id.navigation_faq)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
