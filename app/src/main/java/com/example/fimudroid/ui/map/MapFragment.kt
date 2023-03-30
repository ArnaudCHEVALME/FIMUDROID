package com.example.fimudroid.ui.map

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.*
import com.example.fimudroid.network.retrofit
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalQueries.localDate
import java.util.Date
import java.util.Objects


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

        val root = inflater.inflate(R.layout.fragment_map,container,false)
        map = root.findViewById(R.id.mapView)

        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        map.setTileSource(TileSourceFactory.MAPNIK)

        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

        map.minZoomLevel = 16.5

        map.maxZoomLevel = 21.5

        val fimuBoundingBox : BoundingBox = BoundingBox(47.64836242902998, 6.8783751401231985,47.63332151596629, 6.852366367341309) // vrai
        //val fimuBoundingBox : BoundingBox = BoundingBox(48.64836242902998, 6.8783751401231985,47.63332151596629, 5.852366367341309) // test
        map.setScrollableAreaLimitDouble(fimuBoundingBox)

        map.setMultiTouchControls(true)

        val mapController = map.controller
        mapController.setZoom(18.5)

        val startPoint = GeoPoint( 47.638410197922674,6.862777328835964)

/*
        val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        mLocationOverlay.enableMyLocation()
        map.getOverlays().add(mLocationOverlay)
        //Log.i("Map",mLocationOverlay.myLocation.toDoubleString())*/

/*        val lm: LocationManager
        val gp: GeoPoint
        val provider:String

        lm = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(lm.getAllProviders().contains(LocationManager.GPS_PROVIDER) && lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }
        val lastKnownLoc: Location? = lm.getLastKnownLocation(provider)*/

        val lm: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers: List<String> = lm.getProviders(true) // get enabled providers
        var provider: String? = null

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER
        }

        if (provider == null) {
            throw IllegalStateException("No enabled location provider found")
        }

        val lastKnownLoc: Location? = lm?.getLastKnownLocation(provider)
        val gp: GeoPoint

        if (lastKnownLoc != null) {
            gp = GeoPoint(
                (lastKnownLoc.getLatitude()),
                (lastKnownLoc.getLongitude())
            )
        }else{
            gp = GeoPoint(0,0)
        }

        val locateFloatingButton = root.findViewById<FloatingActionButton>(R.id.floatingButtonLocate)
        if( fimuBoundingBox.contains(gp.latitude,gp.longitude)){
            mapController.setCenter(gp)
            var posMarker : Marker = Marker(map)
            posMarker.icon = resources.getDrawable(R.drawable.map_marker)
            posMarker.position = gp
            posMarker.setInfoWindow(null)
            map.overlays.add(posMarker)
            locateFloatingButton?.show()
            locateFloatingButton?.visibility = View.VISIBLE
            locateFloatingButton.setOnClickListener{
                mapController.animateTo(gp)
            }
        }else{
            locateFloatingButton?.hide()
            locateFloatingButton?.visibility = View.GONE
            mapController.setCenter(startPoint)
        }

        lifecycleScope.launch {
            val stands: List<Stand> = withContext(Dispatchers.IO) {
                api.getStands().data
            }

            for (stand: Stand in stands){
                val markerStand = Marker(map)
                markerStand.position = GeoPoint(stand.latitude.toDouble()+0.0005,stand.longitude.toDouble())
                var titre = stand.libelle+"\n========="
                for(service: Service in stand.services){
                    titre +="\n- "+service.libelle
                }
                markerStand.title = titre

                val typesStand: List<TypeStand> = withContext(Dispatchers.IO){
                    api.getTypesStand().data
                }

                for(typeStand in typesStand){
                    Log.i("map",typeStand.toString())
                }

                when(stand.typestandId){
                    1 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_restaurant)
                    2 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_restaurant)
                    3 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_toilet)
                    4 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_buvette)
                    5 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_boutique)
                    6 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_secours)
                    7 -> markerStand.icon = resources.getDrawable(R.drawable.mdi_eau)
                }

                markerStand.setPanToView(true)

                //markerStand.setInfoWindow(CustomInfoWindow(map,markerStand,stand))
                markerStand.setOnMarkerClickListener { marker, mapView ->
                    view?.findViewById<ChipGroup>(R.id.stand_chipGroup)?.removeAllViews()
                    afficheInfoView(stand, mapController)
                    true
                }
                map.overlays.add(markerStand)
            }
        }

        lifecycleScope.launch {
            val scenes: List<Scene> = withContext(Dispatchers.IO){
                api.getScenes().data
            }

            for (scene : Scene in scenes){
                val sceneMarker : Marker = Marker(map)
                sceneMarker.position = GeoPoint(scene.latitude.toDouble(),scene.longitude.toDouble())
                val titre = scene.libelle+"\n=========\n"+scene.typescene?.libelle
                sceneMarker.title = titre
                sceneMarker.icon = resources.getDrawable(R.drawable.mdi_concert)
                sceneMarker.setPanToView(true)

                sceneMarker.setOnMarkerClickListener{marker,mapView ->
                    afficheInfoView(scene,mapController)
                    true
                }

                map.overlays.add(sceneMarker)
            }
        }
        return root
    }

    override fun onPause() {
        map.onPause()
        super.onPause()
    }

    override fun onResume() {
        map.onResume()
        super.onResume()
    }

    fun afficheInfoView(lieu: Any, mapController: IMapController){
        val card = view?.findViewById<CardView>(R.id.cards_map)
        card?.visibility = View.VISIBLE

        val info_stand = view?.findViewById<ConstraintLayout>(R.id.stand_constraint)
        val info_scene = view?.findViewById<ConstraintLayout>(R.id.scene_constraint)


        //val info_view = StandInfoView(requireContext(),stand)

        val titre = view?.findViewById<TextView>(R.id.info_titre)
        val findButton = view?.findViewById<ImageButton>(R.id.info_find_button)
        val closeButton = view?.findViewById<ImageButton>(R.id.info_close_button)

        if (lieu is Stand){
            info_scene?.visibility = View.GONE
            info_stand?.visibility = View.VISIBLE

            val stand : Stand = lieu
            val standLocation =
                GeoPoint(stand.latitude.toDouble(), stand.longitude.toDouble())
            val standServicesGroup = view?.findViewById<ChipGroup>(R.id.stand_chipGroup)

            titre?.text = stand.libelle
            for (service: Service in stand.services) {
                val serviceChip: Chip = Chip(context)
                serviceChip.text = service.libelle
                standServicesGroup?.addView(serviceChip)
            }

            mapController.animateTo(standLocation)
            closeButton?.setOnClickListener {
                view?.findViewById<CardView>(R.id.cards_map)?.visibility = View.INVISIBLE
                standServicesGroup?.removeAllViews()
            }

            findButton?.setOnClickListener {
                mapController.animateTo(standLocation)
            }
        }else{
            info_scene?.visibility = View.VISIBLE
            info_stand?.visibility = View.GONE

            val scene : Scene = lieu as Scene
            val sceneLocation =
                GeoPoint(scene.latitude.toDouble(), scene.longitude.toDouble())
            val concertTextView = view?.findViewById<TextView>(R.id.scene_concert)
            val artisteTextView = view?.findViewById<TextView>(R.id.scene_artiste)
            val genreTextView = view?.findViewById<TextView>(R.id.scene_genre)


            titre?.text = "Scène de "+scene.libelle

            var nextConcert : Concert
            lifecycleScope.launch {
                val concerts: List<Concert> = withContext(Dispatchers.IO){
                    api.getConcerts().data
                }

                val concertByScene = concerts.groupBy { it.scene }
                Log.i("map2",concertByScene.toString())
                val c = concertByScene[scene]?.filter { LocalDate.now().isBefore(LocalDate.parse(it.date_debut)) }?.filter { LocalTime.now().isBefore(LocalTime.parse(it.heure_debut)) }?.sortedBy { it.heure_debut }?.first() ?: null
                //Log.i("map",c.toString())
                if (c === null){
                    concertTextView?.text = "Plus de concert"
                    artisteTextView?.text = ""
                    genreTextView?.text = ""
                }else{
                    concertTextView?.text = c?.heure_debut?.dropLast(3)+" - "+c?.heure_fin?.dropLast(3)
                    artisteTextView?.text = c?.artiste?.nom
                    genreTextView?.text = c?.artiste?.genres?.get(0)?.libelle
                }


                /*for (concert: Concert in concerts){
                    if (concert.sceneId == scene.id){
                        nextConcert = concert
                        Log.i("map",concert.date_debut)
                        concertTextView?.text = nextConcert.heure_debut.dropLast(3)+" - "+nextConcert.heure_fin.dropLast(3)
                        artisteTextView?.text = nextConcert.artiste?.nom
                        genreTextView?.text = nextConcert.artiste?.genres?.get(0)?.libelle
                        break
                    }
                }*/



            }



            mapController.animateTo(sceneLocation)
            closeButton?.setOnClickListener {
                view?.findViewById<CardView>(R.id.cards_map)?.visibility = View.INVISIBLE
            }

            findButton?.setOnClickListener {
                mapController.animateTo(sceneLocation)
            }
        }






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
