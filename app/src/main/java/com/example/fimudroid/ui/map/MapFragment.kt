package com.example.fimudroid.ui.map

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.*
import com.example.fimudroid.network.retrofit
import com.google.android.material.bottomsheet.BottomSheetDialog
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
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapFragment : Fragment() {
    private lateinit var bottomSheetDialogStand: BottomSheetDialog
    private lateinit var bottomSheetDialogScene: BottomSheetDialog
    private lateinit var bottomSheetStandView: View
    private lateinit var bottomSheetSceneView: View

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATE_ME_PERM -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), "Permission accordée", Toast.LENGTH_SHORT)
                        .show()
                    refreshFragment()
                } else {
                    Log.d("TAG", "Permission refusée")
                    Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    private var locationListener: LocationListener? = null
    private lateinit var map: MapView
    private val LOCATE_ME_PERM = 414;
    private lateinit var mapFilterAdapter: MapFilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Si la navigation n'est pas activée, proposer d'aller aux paramètres de localisation
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Activation de la navigation")
                .setMessage("La navigation n'est pas activée. Voulez-vous l'activer maintenant ?")
                .setCancelable(false)
                .setPositiveButton("Oui") { dialog, id ->
                    // Ouverture des paramètres de localisation pour activer la navigation
                    val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(settingsIntent)
                }
                .setNegativeButton("Non") { dialog, id ->
                    // Fermeture de la boîte de dialogue et arrêt de l'application
                    dialog.cancel()
                    requireActivity().finish()
                }
            val alert = builder.create()
            alert.show()
        } else if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Demande la permission d'accès à la géolocalisation
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATE_ME_PERM
            )
        } else {
            map = MapView(requireContext())
            val root = inflater.inflate(R.layout.fragment_map, container, false)
            map = root.findViewById(R.id.mapView)

            Configuration.getInstance().load(
                requireContext(),
                PreferenceManager.getDefaultSharedPreferences(requireContext())
            )
            map.setTileSource(TileSourceFactory.MAPNIK)

            map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)

            map.minZoomLevel = 16.5

            map.maxZoomLevel = 21.5


            val fimuBoundingBox: BoundingBox = BoundingBox(
                47.64836242902998,
                6.8783751401231985,
                47.63332151596629,
                6.852366367341309
            ) // vrai
            //val fimuBoundingBox : BoundingBox = BoundingBox(48.64836242902998, 6.8783751401231985,47.63332151596629, 5.852366367341309) // test
            map.setScrollableAreaLimitDouble(fimuBoundingBox)

            map.setMultiTouchControls(true)

            val mapController = map.controller
            mapController.setZoom(18.5)


            val startPoint = GeoPoint(47.638410197922674, 6.862777328835964)

            val lm: LocationManager =
                context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
            val gp: GeoPoint = GeoPoint(0, 0)

            mapController.setCenter(startPoint)


            var posMarker: Marker = Marker(map)
            posMarker.icon = ResourcesCompat.getDrawable(resources, R.drawable.map_user, null)
            posMarker.setInfoWindow(null)


            val location = getLocation(requireContext(), posMarker)
            val latitude = location.first
            val longitude = location.second
            gp.latitude = latitude
            gp.longitude = longitude


            val locateFloatingButton =
                root.findViewById<FloatingActionButton>(R.id.floatingButtonLocate)
            /*
            if (fimuBoundingBox.contains(gp.latitude, gp.longitude)) {


                locateFloatingButton?.show()
                locateFloatingButton?.visibility = View.VISIBLE
                locateFloatingButton.setOnClickListener {

                    mapController.animateTo(gp)
                    posMarker.position = GeoPoint(latitude, longitude)
                    map.overlays.add(posMarker)

                }
            } else {
                locateFloatingButton.hide()
            }
            */
            /*
            val recyclerView = inflater.inflate(R.layout.bottom_sheet_map_filter, container, false)
                .findViewById<RecyclerView>(R.id.map_filter_recycler_view)
            lifecycleScope.launch {
                val types: List<TypeStand> = withContext(Dispatchers.IO) {
                    api.getTypesStand().data
                }
                val mapFilterAdapter = MapFilterAdapter(types)
                recyclerView.adapter = mapFilterAdapter
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        recyclerView.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
           */

            // Ajout des overlays pour les stands
            lifecycleScope.launch {
                val stands: List<Stand> = withContext(Dispatchers.IO) {
                    api.getStands().data
                }

                for (stand: Stand in stands) {
                    val markerStand = Marker(map)
                    markerStand.position =
                        GeoPoint(stand.latitude.toDouble() + 0.000005, stand.longitude.toDouble())
                    var titre = stand.libelle + "\n========="
                    for (service: Service in stand.services) {
                        titre += "\n- " + service.libelle
                    }
                    markerStand.title = titre
                    markerStand.icon = setIconForStand(stand)
                    markerStand.setPanToView(true)
                    markerStand.setOnMarkerClickListener { marker, mapView ->
                        bottomSheetDialogStand.findViewById<ChipGroup>(R.id.servicesChipGroup)?.removeAllViews()
                        updateBottomSheetInfoStand(stand, mapController)
                        bottomSheetDialogStand.show()
                        true
                    }

                    map.invalidate()
                    map.overlays.add(markerStand)
                }
                /*

                val exempleStands: List<Stand> = withContext(Dispatchers.IO) {
                    listOf(
                        Stand(
                            1,
                            1,
                            "47.6372767950151",
                            "Stand 1",
                            "6.860763451177344",
                            listOf(
                                Service(
                                    1,
                                    "Service 1"
                                )
                            ),
                            TypeStand(
                                1,
                                "Type 1"
                            )
                        ),
                        Stand(
                            2,
                            2,
                            "47.6372767950151",
                            "Stand 2",
                            "6.860863451177344",
                            listOf(
                                Service(
                                    2,
                                    "Service 2"
                                )
                            ),
                            TypeStand(
                                2,
                                "Type 2"
                            )
                        ),
                        Stand(
                            3,
                            3,
                            "47.6372767950151",
                            "Stand 3",
                            "6.860963451177344",
                            listOf(
                                Service(
                                    3,
                                    "Service 3"
                                )
                            ),
                            TypeStand(
                                3,
                                "Type 3"
                            )
                        ),
                        Stand(
                            4,
                            4,
                            "47.6372767950151",
                            "Stand 4",
                            "6.861063451177344",
                            listOf(
                                Service(
                                    4,
                                    "Service 4"
                                )
                            ),
                            TypeStand(
                                4,
                                "Type 4"
                            )
                        ),
                        Stand(
                            5,
                            5,
                            "47.6372767950151",
                            "Stand 5",
                            "6.861163451177344",
                            listOf(
                                Service(
                                    5,
                                    "Service 5"
                                )
                            ),
                            TypeStand(
                                5,
                                "Type 5"
                            )
                        ),
                        Stand(
                            6,
                            6,
                            "47.6372767950151",
                            "Stand 6",
                            "6.861263451177344",
                            listOf(
                                Service(
                                    6,
                                    "Service 6"
                                )
                            ),
                            TypeStand(
                                6,
                                "Type 6"
                            )
                        ),
                        Stand(
                            7,
                            7,
                            "47.6372767950151",
                            "Stand 7",
                            "6.861363451177344",
                            listOf(
                                Service(
                                    7,
                                    "Service 7"
                                )
                            ),
                            TypeStand(
                                7,
                                "Type 7"
                            )
                        ),
                        Stand(
                            8,
                            8,
                            "47.6372767950151",
                            "Stand 8",
                            "6.861463451177344",
                            listOf(
                                Service(
                                    8,
                                    "Service 8"
                                )
                            ),
                            TypeStand(
                                8,
                                "Type 8"
                            )
                        ),
                        Stand(
                            9,
                            9,
                            "47.6372767950151",
                            "Stand 9",
                            "6.861563451177344",
                            listOf(
                                Service(
                                    9,
                                    "Service 9"
                                )
                            ),
                            TypeStand(
                                9,
                                "Type 9"
                            )
                        ),
                        Stand(
                            10,
                            10,
                            "47.6372767950151",
                            "Stand 10",
                            "6.861663451177344",
                            listOf(
                                Service(
                                    10,
                                    "Service 10"
                                )
                            ),
                            TypeStand(
                                10,
                                "Type 10"
                            )
                        ),
                        Stand(
                            11,
                            11,
                            "47.6372767950151",
                            "Stand 11",
                            "6.861763451177344",
                            listOf(
                                Service(
                                    11,
                                    "Service 11"
                                )
                            ),
                            TypeStand(
                                11,
                                "Type 11"
                            )
                        ),
                        Stand(
                            12,
                            12,
                            "47.6372767950151",
                            "Stand 12",
                            "6.861863451177344",
                            listOf(
                                Service(
                                    12,
                                    "Service 12"
                                )
                            ),
                            TypeStand(
                                12,
                                "Type 12"
                            )
                        ),
                        Stand(
                            13,
                            13,
                            "47.6372767950151",
                            "Stand 13",
                            "6.861963451177344",
                            listOf(
                                Service(
                                    13,
                                    "Service 13"
                                )
                            ),
                            TypeStand(
                                13,
                                "Type 13"
                            )
                        ),
                    )
                }

                for (stand: Stand in exempleStands) {
                    val markerStand = Marker(map)
                    markerStand.position =
                        GeoPoint(stand.latitude.toDouble() + 0.000005, stand.longitude.toDouble())
                    var titre = stand.libelle + "\n========="
                    for (service: Service in stand.services) {
                        titre += "\n- " + service.libelle
                    }
                    markerStand.title = titre

                    when (stand.typestandId) {
                        1 -> markerStand.icon = resources.getDrawable(R.drawable.map_resto)
                        2 -> markerStand.icon = resources.getDrawable(R.drawable.map_secours)
                        3 -> markerStand.icon = resources.getDrawable(R.drawable.map_buvette)
                        4 -> markerStand.icon = resources.getDrawable(R.drawable.map_boutique)
                        5 -> markerStand.icon = resources.getDrawable(R.drawable.map_toilet)
                        6 -> markerStand.icon = resources.getDrawable(R.drawable.map_eau)
                        7 -> markerStand.icon = resources.getDrawable(R.drawable.map_scene)
                        8 -> markerStand.icon = resources.getDrawable(R.drawable.map_entree)
                        9 -> markerStand.icon = resources.getDrawable(R.drawable.map_sortie)
                        10 -> markerStand.icon = resources.getDrawable(R.drawable.map_info)
                        11 -> markerStand.icon = resources.getDrawable(R.drawable.map_parking)
                        12 -> markerStand.icon = resources.getDrawable(R.drawable.map_parking_velo)
                        13 -> markerStand.icon = resources.getDrawable(R.drawable.map_stand)
                    }
                    markerStand.setPanToView(true)
                    markerStand.setOnMarkerClickListener { marker, mapView ->
                        view?.findViewById<ChipGroup>(R.id.stand_chipGroup)?.removeAllViews()
                        afficheInfoView(stand, mapController)
                        true
                    }

                    map.invalidate()
                    map.overlays.add(markerStand)
                }
*/
            }

// Ajout des overlays pour les scènes
            lifecycleScope.launch {
                val scenes: List<Scene> = withContext(Dispatchers.IO) {
                    api.getScenes().data
                }

                for (scene: Scene in scenes) {
                    val sceneMarker = Marker(map)
                    sceneMarker.position =
                        GeoPoint(scene.latitude.toDouble(), scene.longitude.toDouble())
                    val titre = scene.libelle + "\n=========\n" + scene.typescene?.libelle
                    sceneMarker.title = titre
                    sceneMarker.icon = resources.getDrawable(R.drawable.map_scene)
                    sceneMarker.setPanToView(true)

                    sceneMarker.setOnMarkerClickListener { marker, mapView ->
                        updateBottomSheetInfoScene(scene, mapController)
                        bottomSheetDialogScene.show()
                        true
                    }

                    map.invalidate()
                    map.overlays.add(sceneMarker)
                }
            }

            bottomSheetStandView = inflater.inflate(R.layout.map_stand_fragment, container, false)
            bottomSheetDialogStand = BottomSheetDialog(requireContext())
            bottomSheetDialogStand.setContentView(bottomSheetStandView)

            bottomSheetSceneView = inflater.inflate(R.layout.map_scene_fragment, container, false)
            bottomSheetDialogScene = BottomSheetDialog(requireContext())
            bottomSheetDialogScene.setContentView(bottomSheetSceneView)

            return root
        }

// Add the else block to show a message
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL

        val textView = TextView(requireContext())
        textView.text = "Pas de permission pour la localisation\n"
        textView.gravity = Gravity.CENTER

        val button = Button(requireContext())
        button.text = "Refresh"
        button.setOnClickListener {
            refreshFragment()
        }

        linearLayout.addView(textView)
        linearLayout.addView(button)
        return linearLayout
    }

    private fun updateBottomSheetInfoStand(stand: Stand, mapController: IMapController) {
        val standTitleTextView = bottomSheetStandView.findViewById<TextView>(R.id.libelleStand)
        val standServicesGroup = bottomSheetStandView.findViewById<ChipGroup>(R.id.servicesChipGroup)
        val standImage = bottomSheetStandView.findViewById<ImageView>(R.id.imageStand)

        standTitleTextView.text = stand.libelle
        setIconForStand(stand)?.let { standImage.setImageDrawable(it) }
        for (service: Service in stand.services) {
            val serviceChip = Chip(requireContext())
            serviceChip.text = service.libelle
            standServicesGroup?.addView(serviceChip)
        }
        val standLocation = GeoPoint(stand.latitude.toDouble(), stand.longitude.toDouble())
        mapController.animateTo(standLocation)
    }

    private fun updateBottomSheetInfoScene(scene: Scene, mapController: IMapController) {
        val sceneTitleTextView = bottomSheetSceneView.findViewById<TextView>(R.id.libelleScene)
        val concertTextView = bottomSheetSceneView.findViewById<TextView>(R.id.sceneConcert)
        val artisteTextView = bottomSheetSceneView.findViewById<TextView>(R.id.sceneArtiste)
        val genreTextView = bottomSheetSceneView.findViewById<TextView>(R.id.sceneGenre)


        sceneTitleTextView.text = scene.libelle
        var nextConcert: Concert
        lifecycleScope.launch {
            val concerts: List<Concert> = withContext(Dispatchers.IO) {
                api.getConcerts().data
            }

            val concertByScene = concerts.groupBy { it.scene }
            //val c = concertByScene[scene]?.filter { LocalDate.now().isBefore(LocalDate.parse(it.date_debut)) }?.filter { LocalTime.now().isBefore(LocalTime.parse(it.heure_debut)) }?.sortedBy { it.heure_debut }?.first() ?: null
            val c = concerts.first() //pour présentation
            if (c === null) {
                concertTextView?.text = "Plus de concert"
                artisteTextView?.text = ""
                genreTextView?.text = ""
            } else {
                concertTextView?.text =
                    c?.heure_debut?.dropLast(3) + " - " + c?.heure_fin?.dropLast(3)
                artisteTextView.setOnClickListener{
                    clickConcert(c)
                    bottomSheetDialogScene.dismiss()
                }
                artisteTextView?.text = c?.artiste?.nom

                genreTextView?.text = c?.artiste?.genres?.get(0)?.libelle
            }

        }

        val sceneLocation = GeoPoint(scene.latitude.toDouble(), scene.longitude.toDouble())
        mapController.animateTo(sceneLocation)
    }

    private fun setIconForStand(stand: Stand): Drawable? {
        return when (stand.typestand.libelle) {
            "Bar à eau" -> resources.getDrawable(R.drawable.map_eau)
            "Boutique" -> resources.getDrawable(R.drawable.map_boutique)
            "Buvette" -> resources.getDrawable(R.drawable.map_buvette)
            "Entrées" -> resources.getDrawable(R.drawable.map_entree)
            "FIMU des Enfants" -> resources.getDrawable(R.drawable.map_toilet)
            "Navette" -> resources.getDrawable(R.drawable.map_navette)
            "Parking vélos" -> resources.getDrawable(R.drawable.map_parking_velo)
            "Partenaire" -> resources.getDrawable(R.drawable.map_stand)
            "Point Infos" -> resources.getDrawable(R.drawable.map_info)
            "Prévention" -> resources.getDrawable(R.drawable.map_prevention)
            "Secours" -> resources.getDrawable(R.drawable.map_secours)
            "Stand alimentaire" -> resources.getDrawable(R.drawable.map_resto)
            else -> {
                resources.getDrawable(R.drawable.map_stand)}
        }
    }



    override fun onPause() {
        //map.onPause()
        super.onPause()
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener?.let { locationManager.removeUpdates(it) }

    }

    override fun onResume() {
        //map.onResume()
        super.onResume()
    }

    fun refreshFragment() {
        val fragmentManager = requireFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(this.id, this.javaClass.newInstance())
        fragmentTransaction.commit()
    }

    fun afficheInfoView(lieu: Any, mapController: IMapController) {
        val card = view?.findViewById<CardView>(R.id.cards_map)
        card?.visibility = View.VISIBLE
        val info_scene = view?.findViewById<ConstraintLayout>(R.id.scene_constraint)


        //val info_view = StandInfoView(requireContext(),stand)

        val titre = view?.findViewById<TextView>(R.id.info_titre)
        val findButton = view?.findViewById<ImageButton>(R.id.info_find_button)
        val closeButton = view?.findViewById<ImageButton>(R.id.info_close_button)

        if (lieu is Stand) {

        } else {
            info_scene?.visibility = View.VISIBLE

            val scene: Scene = lieu as Scene
            val sceneLocation =
                GeoPoint(scene.latitude.toDouble(), scene.longitude.toDouble())
            val concertTextView = view?.findViewById<TextView>(R.id.scene_concert)
            val artisteTextView = view?.findViewById<TextView>(R.id.scene_artiste)
            val genreTextView = view?.findViewById<TextView>(R.id.scene_genre)


            titre?.text = "Scène de " + scene.libelle

            var nextConcert: Concert
            lifecycleScope.launch {
                val concerts: List<Concert> = withContext(Dispatchers.IO) {
                    api.getConcerts().data
                }

                val concertByScene = concerts.groupBy { it.scene }
                //val c = concertByScene[scene]?.filter { LocalDate.now().isBefore(LocalDate.parse(it.date_debut)) }?.filter { LocalTime.now().isBefore(LocalTime.parse(it.heure_debut)) }?.sortedBy { it.heure_debut }?.first() ?: null
                val c = concerts.first() //pour présentation
                if (c === null) {
                    concertTextView?.text = "Plus de concert"
                    artisteTextView?.text = ""
                    genreTextView?.text = ""
                } else {
                    concertTextView?.text =
                        c?.heure_debut?.dropLast(3) + " - " + c?.heure_fin?.dropLast(3)
                    artisteTextView?.text = c?.artiste?.nom
                    genreTextView?.text = c?.artiste?.genres?.get(0)?.libelle
                }

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


    fun getLocation(context: Context, posMarker: Marker): Pair<Double, Double> {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val REQUEST_LOCATION_PERMISSION = 414

        // Vérification de la permission ACCESS_FINE_LOCATION
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Demande de permission si nécessaire
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return Pair(0.0, 0.0)
        }

        // Écouteur de mises à jour de localisation
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.d("myTag", "latitude: ${location.latitude}, longitude: ${location.longitude}")
                posMarker.position = GeoPoint(location.latitude, location.longitude)
                map.invalidate()

            }

            override fun onProviderDisabled(provider: String) {
                // Non utilisé
            }

            override fun onProviderEnabled(provider: String) {
                // Non utilisé
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
                // Non utilisé
            }
        }

        // Enregistrement de l'écouteur de mises à jour de localisation
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            3000, // Mettre à jour toutes les 3 secondes
            2f, // Mettre à jour même si la position n'a pas bougé
            locationListener as LocationListener
        )

        // Récupération de la dernière position connue
        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        return if (lastLocation != null) {
            Pair(lastLocation.latitude, lastLocation.longitude)
        } else {
            Pair(0.0, 0.0)
        }
    }

    fun clickConcert(c : Concert){
        val bundle = Bundle()
        bundle.putInt("id_art", c.artisteId)
        requireView().findNavController()
            .navigate(R.id.action_navigation_plan_to_artisteDetailsFragment, bundle)
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


