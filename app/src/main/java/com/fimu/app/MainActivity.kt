package com.fimu.app

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fimu.app.databinding.ActivityMainBinding
import com.fimu.app.network.FimuApiService
import com.fimu.app.network.retrofit
import com.fimu.app.ui.map.MapFiltersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
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

        // Get the Toolbar from the layout
        toolbar = findViewById(com.fimu.app.R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Get the NavHostFragment from the layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(com.fimu.app.R.id.nav_host_fragment_activity_main) as NavHostFragment

        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView

        val navController = navHostFragment.navController

        val FAQButton = findViewById<MaterialButton>(com.fimu.app.R.id.FAQButton)
        val ArtistFilterButton = findViewById<MaterialButton>(com.fimu.app.R.id.ArtistFilterButton)
        val MapFilterButton = findViewById<MaterialButton>(com.fimu.app.R.id.MapFilterButton)
        val buttonsMap = mapOf(
            com.fimu.app.R.id.navigation_news to FAQButton,
            com.fimu.app.R.id.navigation_artiste_list to ArtistFilterButton,
            com.fimu.app.R.id.navigation_plan to MapFilterButton
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            buttonsMap.forEach { (destinationId, button) ->
                button.visibility = if (destination.id == destinationId) View.VISIBLE else View.GONE
            }
        }
        FAQButton.setOnClickListener {
            navController.navigate(com.fimu.app.R.id.navigation_faq)
        }
        ArtistFilterButton.setOnClickListener {
            showArtisteBottomSheet()
        }
        MapFilterButton.setOnClickListener {
            val mapFiltersFragment = MapFiltersFragment()
            mapFiltersFragment.show(supportFragmentManager, "MapFiltersFragmentTag")
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.fimu.app.R.id.navigation_news,
                com.fimu.app.R.id.navigation_artiste_list,
                com.fimu.app.R.id.navigation_plan,
                com.fimu.app.R.id.navigation_programmation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NavigationBarView.OnItemSelectedListener { item ->
            val currentFragment = navController.currentDestination?.id
            val newFragment = when (item.itemId) {
                com.fimu.app.R.id.navigation_news -> com.fimu.app.R.id.navigation_news
                com.fimu.app.R.id.navigation_artiste_list -> com.fimu.app.R.id.navigation_artiste_list
                com.fimu.app.R.id.navigation_plan -> com.fimu.app.R.id.navigation_plan
                com.fimu.app.R.id.navigation_programmation -> com.fimu.app.R.id.navigation_programmation
                else -> null
            }
            if (currentFragment != newFragment) {
                navController.navigate(item.itemId)
            }
            true
        }
        requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Crédit : Samson, Réalisation : Gabin
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
        val navController = Navigation.findNavController(this,
            com.fimu.app.R.id.nav_host_fragment_activity_main
        )
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun showBottomSheet(layoutResId: Int, heightResId: Int) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(layoutResId, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.peekHeight = 0

        val layoutParams = bottomSheetView.layoutParams
        layoutParams.height = resources.getDimensionPixelSize(heightResId)
        bottomSheetView.layoutParams = layoutParams

        bottomSheetDialog.show()
    }


    private fun showArtisteBottomSheet() {
        showBottomSheet(
            com.fimu.app.R.layout.bottom_sheet_artiste_filter,
            com.fimu.app.R.dimen.artiste_bottom_sheet_height
        )
    }
}
