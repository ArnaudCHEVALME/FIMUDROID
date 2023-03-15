package com.example.fimudroid

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.database.models.Stand
import com.example.fimudroid.databinding.ActivityMapBinding
import com.example.fimudroid.ui.news.NewsViewModel
import com.example.fimudroid.ui.stands.StandViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem


/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        map = MapView(inflater.context)

        val db :FimuDB = FimuDB.getInstance(requireContext())

        val standViewModel : StandViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[StandViewModel::class.java]

        var standList : LiveData<List<Stand>> = standViewModel.getAllStands()

/*
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)*/

        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))


        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(18.5) //18.5

        val startPoint = GeoPoint( 47.638410197922674,6.862777328835964)
        mapController.setCenter(startPoint)

        //your items
        val items = ArrayList<OverlayItem>()
        /*items.add(OverlayItem("Scène", "Kiosque", GeoPoint(47.63830808584398, 6.8630603018852705)))
        items.add(OverlayItem("Bar", "La belle bête", GeoPoint(47.638410197922674,6.862777328835964)))*/

        standViewModel.getAllStands().observe(viewLifecycleOwner){stands ->
            /*for (stand: Stand in stands){
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
            }, requireContext())
            overlay.setFocusItemsOnTap(true)

            map.overlays.add(overlay)*/

            for (stand: Stand in stands){
                var markerStand = Marker(map)
                markerStand.setPosition(GeoPoint(stand.longitude.toDouble(),stand.latitude.toDouble()))
                markerStand.setTitle(stand.libelle)
                markerStand.setIcon(resources.getDrawable(R.drawable.stand))
                map.overlays.add(markerStand)
            }

            var sceneTestMarker = Marker(map)
            sceneTestMarker.setPosition(GeoPoint(47.63830808584398,6.8630603018852705))
            sceneTestMarker.setTitle("Le Kiosque")
            sceneTestMarker.setIcon(resources.getDrawable(R.drawable.microphone))
            map.overlays.add(sceneTestMarker)

            var standTestMarker = Marker(map)
            standTestMarker.setPosition(GeoPoint(47.638410197922674,6.862777328835964))
            standTestMarker.setTitle("La belle bête")
            standTestMarker.setIcon(resources.getDrawable(R.drawable.stand))
            map.overlays.add(standTestMarker)


        }


        standViewModel.getAllStands()


        return map
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onPause() {
        if (map != null){
            map.onPause()
        }
        super.onPause()
    }

    override fun onResume() {
        if (map != null){
            map.onResume()
        }
        super.onResume()
    }
}