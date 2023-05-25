package com.fimu.app.ui.planning

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.fimu.app.R
import com.fimu.app.network.FimuApiService
import com.fimu.app.network.models.Concert
import com.fimu.app.network.models.Scene
import com.fimu.app.network.retrofit
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.Month
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class PlanningFragment : Fragment() {

    private lateinit var planningLayout: CustomLinearLayout
    private lateinit var _concerts: List<Concert>
    private lateinit var dayBtns: LinearLayout
    private lateinit var catsLegend: GridLayout
    private lateinit var concertsByScene: Map<Scene, List<Concert>>
    private lateinit var dates: List<String>
    private lateinit var concertsByDateByScene: Map<String, Map<Scene, List<Concert>>>
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup

    private lateinit var ScrollViewPlanning: ScrollView
    //private lateinit var sceneLayout: CustomLinearLayout
    private lateinit var horizontalScrollView: HorizontalScrollView
    private lateinit var horizontalScrollViewPlanning: HorizontalScrollView
    private lateinit var scrollScene: ScrollView
    private lateinit var HourLayout: CustomView
    //private lateinit var scrollSceneX: HorizontalScrollView
    private lateinit var  SceneView:  CustomViewScene

    var lastX = 0f
    var lastY = 0f


    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_planning, container, false)
        planningLayout = root.findViewById(R.id.planning_vertical_linear_layout)
        toggleButtonGroup = root.findViewById(R.id.toggleButton)
        catsLegend = root.findViewById(R.id.cat_legend_linear_layout)
        ScrollViewPlanning = root.findViewById(R.id.scrollViewA)
        //sceneLayout = root.findViewById(R.id.SceneLayout)
        // Récupérer la référence de la vue HorizontalScrollView
        horizontalScrollView = root.findViewById(R.id.CustomHorizontalScrollView)
        horizontalScrollViewPlanning = root.findViewById(R.id.HorizontalScrollPlanning)
        HourLayout = root.findViewById(R.id.customView)
        SceneView = root.findViewById(R.id.Scenewrite)

        scrollScene = root.findViewById(R.id.scrollViewScene)
        //scrollSceneX = root.findViewById(R.id.scrollviewscenex)


        catsLegend.columnCount = 2
        catsLegend.useDefaultMargins = true

        lifecycleScope.launch {

            _concerts = withContext(Dispatchers.IO) {
                api.getConcerts().data
            }

            concertsByDateByScene = _concerts
                .groupBy { concert -> concert.date_debut }
                .mapValues { (_, concerts) ->
                    concerts.groupBy { concert -> concert.scene }
                }

            dates = concertsByDateByScene.keys.sorted()
            initBtns()
            concertsByScene = concertsByDateByScene[dates[0]] ?: emptyMap()
            initPlanningView(270f)
        }

        SceneView.tabword = arrayOf("Scene 1", "Auditorium", "Grande Salle", "CAMPUS", "CONCERTS DE RUE", "CORBIS", "GRANIT", "JAZZ", "KIOSQUE", "L'ARSENAL", "SALLE DES FÊTES", "SAVOUREUSE", "SHOWCASE FB", "St CHRISTOPHE", "SALLE DES FÊTES","SCENE DES ENFANTS", "HOTEL DU DEPART")
////////////////////////////////////////////////////////////// Définir un écouteur tactile pour la vue NestedScrollView
        HourLayout.text = "           16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                "


        scrollScene.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            planningLayout.scrollTo(0, scrollY)
        }

        planningLayout.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            scrollScene.scrollTo(0, scrollY)
        }

        horizontalScrollViewPlanning.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            horizontalScrollView.scrollTo(scrollX, 0)
        }

        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            horizontalScrollViewPlanning.scrollTo(scrollX, 0)
        }
        horizontalScrollViewPlanning.translationY = -100f

        horizontalScrollViewPlanning.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                    ScrollViewPlanning.requestDisallowInterceptTouchEvent(true)
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = (event.y - lastY) * 0.5f
                    scrollScene.scrollBy(0, -deltaY.toInt())
                    planningLayout.scrollBy(0, -deltaY.toInt())
                    horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }

        return root
    }

    private fun initBtns() {
        for (d in dates) {
            val button = MaterialButton(requireContext(), null, R.attr.materialButtonOutlinedStyle)
            button.id = View.generateViewId()
            // format Day + Mount name
            val jour = d.split("-")[2].toInt()
            val mois = getMonth(Month.of(d.split("-")[1].toInt()))
            button.text = "$jour $mois"
            button.setOnClickListener {
                concertsByScene = concertsByDateByScene[d]!!
                if (d == "2023-05-26")
                {
                    SceneView.tabword = arrayOf("Scene 1", "Auditorium", "Grande Salle", "CAMPUS", "CONCERTS DE RUE", "CORBIS", "GRANIT", "JAZZ", "KIOSQUE", "L'ARSENAL", "SALLE DES FÊTES", "SAVOUREUSE", "SHOWCASE FB", "St CHRISTOPHE", "SALLE DES FÊTES","SCENE DES ENFANTS", "HOTEL DU DEPART")
                    HourLayout.text = "           16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                "
                    initPlanningView(270f)
                }
                if (d == "2023-05-27")
                {
                    SceneView.tabword = arrayOf("Scene 1", "Auditorium", "Grande Salle", "C.C.I", "CAMPUS", "CONCERTS DE RUE", "CORBIS", "GRANIT", "HOTEL DU DEPART", "JAZZ", "KIOSQUE", "L'ARSENAL", "SALLE DES FÊTES", "SAVOUREUSE", "SCENE DES ENFANTS", "SHOWCASE FB","St CHRISTOPHE")
                    HourLayout.text = "           14h                                 15h                                 16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                "
                    initPlanningView(0f)
                }
                if (d == "2023-05-28")
                {
                    SceneView.tabword = arrayOf("Scene 1", "Auditorium", "Grande Salle", "C.C.I", "CAMPUS", "CONCERTS DE RUE", "CORBIS", "GRANIT", "HOTEL DU DEPART", "JAZZ", "KIOSQUE", "L'ARSENAL", "SALLE DES FÊTES", "SAVOUREUSE", "SCENE DES ENFANTS", "SHOWCASE FB","St CHRISTOPHE")
                    HourLayout.text = "           14h                                 15h                                 16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                 09h                                "
                    initPlanningView(0f)
                }
                HourLayout.invalidate()
                SceneView.invalidate()


            }
            toggleButtonGroup.addView(button)
        }
    }


    private fun getMonth(month: Month) : String{
        return when (month) {
            Month.JANUARY -> "Janvier"
            Month.FEBRUARY -> "Février"
            Month.MARCH-> "Mars"
            Month.APRIL -> "Avril"
            Month.MAY -> "Mai"
            Month.JUNE -> "Juin"
            Month.JULY -> "Juillet"
            Month.AUGUST -> "Août"
            Month.SEPTEMBER -> "Séptembre"
            Month.OCTOBER -> "Octobre"
            Month.NOVEMBER -> "Novembre"
            else -> "Décembre"
        }
    }

    private fun initLegend() {
        catsLegend.removeAllViews()
        val categories = concertsByScene.values.flatten().map { it.artiste?.categorie }.distinct()

        // Définir l'ordre personnalisé des catégories
        val customOrder = listOf("Musique actuelle", "Sono Mondiale", "Musiques classiques", "Jazz & Musiques Improvisées") // Remplacez avec votre propre ordre

        val sortedCategories = categories.sortedWith(compareBy { customOrder.indexOf(it?.libelle) })

        for ((index, cat) in sortedCategories.withIndex()) {
            // color
            val colorCircle = View(context)
            colorCircle.layoutParams = LinearLayout.LayoutParams(30, 30).apply {
                setMargins(10, 10, 10, 10)
            }
            colorCircle.setBackgroundColor(Color.parseColor(cat?.couleur ?: "#000000"))

            // name
            val legendTextView = TextView(context)
            legendTextView.textSize = 15f
            legendTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(10, 10, 10, 10)
            }
            legendTextView.text = cat?.libelle ?: "??????????"

            // Add the color circle and text to a new LinearLayout
            val legendView = LinearLayout(context)
            legendView.orientation = LinearLayout.HORIZONTAL
            legendView.gravity = Gravity.CENTER
            legendView.addView(colorCircle)
            legendView.addView(legendTextView)


            // Add the legend view to the grid
            val params = GridLayout.LayoutParams()
            params.columnSpec = GridLayout.spec(index % 2)
            params.rowSpec = GridLayout.spec(index / 2)
            catsLegend.addView(legendView, params)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlanningView(trans: Float) {
        planningLayout.removeAllViews()
        val earliestTime =
            concertsByScene
                .minOf { entry: Map.Entry<Scene, List<Concert>> ->
                    entry.value
                        .minOf { concert: Concert -> concert.heure_debut }
                }

        planningLayout.setStartTime(earliestTime)
        //HourLayout.setStartTime(earliestTime)

        val hourSpace = LinearLayout(context)
        val hourLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            100
        )
        hourSpace.layoutParams = hourLayoutParams
        planningLayout.addView(hourSpace)
        initLegend()



        // Add a linearLayout for each scene
        for (scene in concertsByScene.keys.sortedBy { it.libelle }) {
            val linearLayout = LinearLayout(planningLayout.context)
            linearLayout.translationX = trans



            // Set dimensions of the row
            val rowLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                500
            )
            linearLayout.layoutParams = rowLayoutParams

            // Set dimensions of the row
            val rowLayoutParamsscene = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                500
            )
            //sceneLayout.layoutParams = rowLayoutParamsscene

             val sceneName = TextView(linearLayout.context)

            sceneName.hyphenationFrequency = Layout.HYPHENATION_FREQUENCY_NONE
            sceneName.setBackgroundColor(Color.parseColor("#5A5A5A"))
            sceneName.setTypeface(null, Typeface.BOLD)
            sceneName.setTextColor(Color.parseColor("#FFFFFF"))

            // Set dimensions of the text
            val sceneNameLayoutParams = LinearLayout.LayoutParams(
                300,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            sceneNameLayoutParams.gravity = Gravity.CENTER_VERTICAL
            sceneName.layoutParams = sceneNameLayoutParams


            // Set text
            sceneName.text =  scene.libelle
            sceneName.gravity = Gravity.CENTER
            //sceneName.rotation = -90f
            //sceneName.translationX = 150f

            // Set up auto-sizing text
            sceneName.setAutoSizeTextTypeUniformWithConfiguration(
                10,  // The smallest text size in scaled pixels
                20,  // The largest text size in scaled pixels
                2,   // The step size in scaled pixels
                TypedValue.COMPLEX_UNIT_SP // The unit of the text size values (sp)
            )

            // add scene libelle
            //sceneName.setBackgroundColor(Color.parseColor("#0000FF"))
            //sceneLayout.setBackgroundColor(Color.parseColor("#FF0000"))//


/*          val txt = TextView(requireContext());
            txt.setText("hello")
            horizontalScrollView.addView(txt)*/

            // add a ConcertView to the linearLayout for each concert
            val concerts = concertsByScene[scene]?.sortedBy { it.heure_debut } ?: emptyList()
            val concertF = concerts[0]
            val blank = View(linearLayout.context)
            blank.layoutParams = ViewGroup.LayoutParams(
                getTimeDifferenceInMinutes(
                    earliestTime,
                    concertF.heure_debut
                ) * 8, 0
            )
            linearLayout.addView(blank)

            val concertFView = ConcertView(linearLayout.context)
            //concertFView.translationX = -200f
            concertFView.setConcert(concertF)

            // add the view to the LinearLayout
            concertFView.setOnClickListener{clickConcert(concertF)}

            linearLayout.addView(concertFView)
            if (concerts.size > 1) {
                for (i in 0 until concerts.size - 1) {
                    val blankP = View(linearLayout.context)

                    val concertP = concerts[i]
                    val concertN = concerts[i+1]

                    blankP.layoutParams = ViewGroup.LayoutParams(
                        getTimeDifferenceInMinutes(
                            concertP.heure_fin,
                            concertN.heure_debut
                        ) * 8, 100
                    )


                    linearLayout.addView(blankP)

                    val concertView = ConcertView(linearLayout.context)


                    concertView.setConcert(concertN)
                    concertView.setOnClickListener{clickConcert(concertN)}
                    //concertView.setOnClickListener(null)

                    concertView.artisteNameTextView.setOnClickListener { clickConcert(concertN) }

                    linearLayout.addView(concertView)
                }
            }

            planningLayout.addView(linearLayout)
            //sceneLayout.rotation = 90f;
            for (i in 0 until linearLayout.childCount) {
                val childView = linearLayout.getChildAt(i)
                childView.isClickable = false
            }



            //sceneLayout.addView(sceneName)
        }


/*        val layoutParams = HourLayout.layoutParams
        layoutParams.width = 1000
        layoutParams.height = 100
        //HourLayout.requestLayout()
        horizontalScrollView.requestLayout()*/

        val viewTreeObserver = planningLayout.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                val width = planningLayout.width
                val height = planningLayout.height
                Log.d("taille", width.toString())
                Log.d("hauteur", height.toString())

                // LA ON FAIS CE QUE LON VEUX


/*                val layoutParams = HourLayout.layoutParams as ViewGroup.LayoutParams
                layoutParams.width = 1000
                layoutParams.height = 100

                HourLayout.layoutParams = layoutParams

                val parentView = HourLayout.parent as View
                parentView.requestLayout()
                HourLayout.requestLayout()*/

               // Log.d("tailleHour", HourLayout.layoutParams.width.toString())
               // Log.d("hauteurHour", HourLayout.layoutParams.height.toString())
                //invalidate()
                //requestLayout()
                // Supprimez l'écouteur de l'observateur de l'arbre de vue
                planningLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    private fun getTimeDifferenceInMinutes(time1: String, time2: String): Int {
        val localTime1 = LocalTime.parse(time1)
        val localTime2 = LocalTime.parse(time2)
        val minutesDifference = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return minutesDifference.toInt().absoluteValue
    }

    fun clickConcert(c : Concert){
        val bundle = Bundle()
        bundle.putInt("id_art", c.artisteId)
        requireView().findNavController()
            .navigate(R.id.action_programmation_to_artisteDetails, bundle)
    }
}
