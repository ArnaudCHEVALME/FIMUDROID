package com.example.fimudroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.database.models.Stand
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.databinding.ActivityMapBinding
import com.example.fimudroid.ui.stands.StandViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView
    private lateinit var binding: ActivityMapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db :FimuDB = FimuDB.getInstance(this)

        val standViewModel : StandViewModel = StandViewModel(this.application);

        var standList : LiveData<List<Stand>> = standViewModel.getAllStands()

        setContentView(R.layout.activity_map)

        setContentView(R.layout.activity_main)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))


        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(18.5)

        val startPoint = GeoPoint( 47.638410197922674,6.862777328835964)
        mapController.setCenter(startPoint)

        //your items
        val items = ArrayList<OverlayItem>()
        /*items.add(OverlayItem("Scène", "Kiosque", GeoPoint(47.63830808584398, 6.8630603018852705)))
        items.add(OverlayItem("Bar", "La belle bête", GeoPoint(47.638410197922674,6.862777328835964)))*/

        standViewModel.getAllStands().observe(this){stands ->
            for (stand: Stand in stands){
                items.add(OverlayItem("Stand",stand.libelle,GeoPoint(stand.longitude.toDouble(),stand.latitude.toDouble())))
            }

            //the overlay
            var overlay = ItemizedOverlayWithFocus<OverlayItem>(items, object:
                ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index:Int, item:OverlayItem):Boolean {
                    //do something
                    return true
                }
                override fun onItemLongPress(index:Int, item:OverlayItem):Boolean {
                    return false
                }
            }, this)
            overlay.setFocusItemsOnTap(true)

            map.overlays.add(overlay)
        }


        standViewModel.getAllStands()

    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }
}