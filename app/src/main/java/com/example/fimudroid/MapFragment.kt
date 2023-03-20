package com.example.fimudroid

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.network.models.Service
import com.example.fimudroid.network.models.Stand
import com.example.fimudroid.network.retrofit
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map : MapView

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var root = inflater.inflate(R.layout.fragment_map,container,false)
        map = root.findViewById(R.id.mapView)

        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        map.setTileSource(TileSourceFactory.MAPNIK)

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        map.minZoomLevel = 16.5

        map.setMultiTouchControls(true);

        val mapController = map.controller
        mapController.setZoom(18.5)

        val startPoint = GeoPoint( 47.638410197922674,6.862777328835964)
        mapController.setCenter(startPoint)

        lifecycleScope.launch {
            val stands: List<Stand> = withContext(Dispatchers.IO) {
                api.getStands().data
            }

            for (stand: Stand in stands){
                var markerStand = Marker(map)
                markerStand.position = GeoPoint(stand.latitude.toDouble()+0.0005,stand.longitude.toDouble())
                var titre = stand.libelle+"\n========="
                for(service: Service in stand.services){
                    titre +="\n- "+service.libelle
                }
                markerStand.title = titre
                //Log.i("MAP",stand.libelle)
                markerStand.icon = resources.getDrawable(R.drawable.stand)

                //markerStand.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

                markerStand.setPanToView(true)

                //markerStand.setInfoWindow(CustomInfoWindow(map,markerStand,stand))
                markerStand.setOnMarkerClickListener(object: Marker.OnMarkerClickListener {
                    override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {

                        view?.findViewById<CardView>(R.id.cards_map)?.visibility = View.VISIBLE
                        val findButton = view?.findViewById<ImageButton>(R.id.stand_find_button)
                        val closeButton = view?.findViewById<ImageButton>(R.id.stand_close_button)
                        val standLocation = GeoPoint(stand.latitude.toDouble(),stand.longitude.toDouble())
                        val standTitre = view?.findViewById<TextView>(R.id.stand_titre)
                        val standServicesGroup = view?.findViewById<ChipGroup>(R.id.stand_chipGroup)

                        mapController.animateTo(standLocation)
                        closeButton?.setOnClickListener{
                            view?.findViewById<CardView>(R.id.cards_map)?.visibility = View.INVISIBLE
                            standTitre?.text = ""
                            standServicesGroup?.removeAllViews()
                        }

                        findButton?.setOnClickListener{
                            mapController.animateTo(standLocation)
                        }

                        standTitre?.text =  stand.libelle

                        for(service : Service in stand.services){
                            var serviceChip : Chip = Chip(requireContext())
                            serviceChip.text = service.libelle
                            standServicesGroup?.addView(serviceChip)
                        }

                        for(i in 0..40){
                            var serviceChip : Chip = Chip(requireContext())
                            serviceChip.text = "Connard " + i
                            standServicesGroup?.addView(serviceChip)
                        }

                        return true
                    }

                })
                map.overlays.add(markerStand)
            }
        }

        lifecycleScope.launch {
            val scenes: List<Scene> = withContext(Dispatchers.IO){
                api.getScenes().data
            }

            for (scene : Scene in scenes){
                var sceneMarker : Marker = Marker(map)
                sceneMarker.position = GeoPoint(scene.latitude.toDouble(),scene.longitude.toDouble())
                var titre = scene.libelle+"\n=========\n"+scene.typescene.libelle
                sceneMarker.title = titre
                sceneMarker.icon = resources.getDrawable(R.drawable.microphone)
                sceneMarker.setPanToView(true)
                map.overlays.add(sceneMarker)
            }
        }
        return root
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

/*class CustomInfoWindow(mapView: MapView, marker: Marker, stand: Stand): InfoWindow(R.layout.bubble_stand, mapView){

    var marker: Marker
    var stand: Stand
    init {
       this.marker = marker
        this.stand = stand
    }
    override fun onOpen(item: Any?) {
        view.findViewById<TextView>(R.id.bubble_title).setText(stand.libelle)
        view.findViewById<TextView>(R.id.buble_service).setText(stand.services[0].libelle)
        val position: GeoPoint = marker.position
        // Convert the position to screen coordinates
        val projection: Projection = mapView.projection
        val screenPosition: Point = projection.toPixels(position, null)
        // Offset the position to center the InfoWindow above the marker
        val screenHeight: Int = mapView.height
        // Offset the position to show the InfoWindow at the bottom of the screen
        screenPosition.offset(0, screenHeight - mView.height - screenPosition.y)
        // Set the position of the InfoWindow
        mView.layoutParams = MapView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            position,
            MapView.LayoutParams.BOTTOM_CENTER, 0, 0
        )

    }

    override fun onClose() {
        marker.closeInfoWindow()
        super.close()

    }

}*/
