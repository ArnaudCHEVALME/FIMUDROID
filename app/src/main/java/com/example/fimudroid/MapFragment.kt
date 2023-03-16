package com.example.fimudroid

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.network.models.Service
import com.example.fimudroid.network.models.Stand
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow
import org.w3c.dom.Text

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

        val mapController = map.controller
        mapController.setZoom(18.5)

        val startPoint = GeoPoint( 47.638410197922674,6.862777328835964)
        mapController.setCenter(startPoint)

        lifecycleScope.launch {
            val stands: List<Stand> = withContext(Dispatchers.IO) {
                api.getStands().data
            }

            //your items
            /*val items = ArrayList<OverlayItem>()

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
            }, requireContext())
            overlay.setFocusItemsOnTap(true)

            map.overlays.add(overlay)*/
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
