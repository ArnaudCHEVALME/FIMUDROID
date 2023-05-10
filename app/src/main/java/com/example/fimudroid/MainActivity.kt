package com.example.fimudroid

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
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

    private val bottomSheetView by lazy { findViewById<ConstraintLayout>(R.id.artist_bottom_sheet) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
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
        toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Get the NavHostFragment from the layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView

        val navController = navHostFragment.navController

        val FAQButton = findViewById<MaterialButton>(R.id.FAQButton)
        val ArtistFilterButton = findViewById<MaterialButton>(R.id.ArtistFilterButton)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_news) {
                FAQButton.visibility = View.VISIBLE
            } else {
                FAQButton.visibility = View.GONE
            }
            if (destination.id == R.id.navigation_artiste_list) {
                ArtistFilterButton.visibility = View.VISIBLE
            } else {
                ArtistFilterButton.visibility = View.GONE
            }
        }

        FAQButton.setOnClickListener {
            navController.navigate(R.id.navigation_faq)
        }


        ArtistFilterButton.setOnClickListener {
            showArtisteBottomSheet()
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_news,
                R.id.navigation_artiste_list,
                R.id.navigation_plan,
                R.id.navigation_programmation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        NavigationBarView.OnItemSelectedListener { item ->
            val currentFragment = navController.currentDestination?.id
            val newFragment = when (item.itemId) {
                R.id.navigation_news -> R.id.navigation_news
                R.id.navigation_artiste_list -> R.id.navigation_artiste_list
                R.id.navigation_plan -> R.id.navigation_plan
                R.id.navigation_programmation -> R.id.navigation_programmation
                else -> null
            }
            if (currentFragment != newFragment) {
                navController.navigate(item.itemId)
            }
            true
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Crédit : Samson, Réalisation : Gabin
    }

    private fun onCreateView() {
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

    private fun showArtisteBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_artiste_filter, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.peekHeight = 0

        val layoutParams = bottomSheetView.layoutParams
        layoutParams.height = resources.getDimensionPixelSize(R.dimen.artiste_bottom_sheet_height)
        bottomSheetView.layoutParams = layoutParams


        bottomSheetDialog.show()
    }



}
