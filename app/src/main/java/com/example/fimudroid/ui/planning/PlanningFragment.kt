package com.example.fimudroid.ui.planning

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.network.retrofit
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
    //private lateinit var scrollSceneX: HorizontalScrollView

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
            initPlanningView()
        }

        ScrollViewPlanning.isHorizontalScrollBarEnabled = false
        ScrollViewPlanning.isVerticalScrollBarEnabled = false
        ScrollViewPlanning.isNestedScrollingEnabled = false;

        planningLayout.isHorizontalScrollBarEnabled = false
        planningLayout.isVerticalScrollBarEnabled = false


        horizontalScrollViewPlanning.isHorizontalScrollBarEnabled = false
        horizontalScrollViewPlanning.isVerticalScrollBarEnabled = false
        horizontalScrollViewPlanning.isNestedScrollingEnabled = false;

        ScrollViewPlanning.isHorizontalScrollBarEnabled = false
        ScrollViewPlanning.isVerticalScrollBarEnabled = false
        ScrollViewPlanning.isNestedScrollingEnabled = false;



////////////////////////////////////////////////////////////// Définir un écouteur tactile pour la vue NestedScrollView

/*        scrollSceneX.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                    //horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    //horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    scrollScene.scrollBy(0, -deltaY.toInt())
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }*/

        scrollScene.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            ScrollViewPlanning.scrollTo(0, scrollY)
        }
        ScrollViewPlanning.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            scrollScene.scrollTo(0, scrollY)
        }

/*        ScrollViewPlanning.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            horizontalScrollView.scrollTo(scrollX, 0)
        }

        horizontalScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            ScrollViewPlanning.scrollTo(scrollX, 0)
        }*/

/*
        scrollScene.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            ScrollViewPlanning.scrollTo(0, scrollY)
            planningLayout.scrollTo(0, scrollY)
        }
*/

        scrollScene.scrollY = planningLayout.scrollY
        scrollScene.scrollY = ScrollViewPlanning.scrollY
        scrollScene.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                    //horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    //horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    scrollScene.scrollBy(0, -deltaY.toInt())
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        planningLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                    horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    scrollScene.scrollBy(0, -deltaY.toInt())
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        ScrollViewPlanning.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                    horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    scrollScene.scrollBy(0, -deltaY.toInt())
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }


        horizontalScrollView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    //scrollScene.scrollBy(0, -deltaY.toInt())
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
        }
        horizontalScrollViewPlanning.translationY = -100f
        horizontalScrollViewPlanning.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.x
                    lastY = event.y
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY
                    //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                    ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                    horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                    horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                    //scrollScene.scrollBy(0, -deltaY.toInt())
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
                initPlanningView()
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

        for ((index, cat) in categories.sortedBy { it?.libelle }.withIndex()) {
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
            params.rowSpec = GridLayout.spec(index / catsLegend.columnCount, 1f)
            params.columnSpec = GridLayout.spec(index % catsLegend.columnCount, 1f)
            catsLegend.addView(legendView, params)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initPlanningView() {
        planningLayout.removeAllViews()

        val earliestTime =
            concertsByScene
                .minOf { entry: Map.Entry<Scene, List<Concert>> ->
                    entry.value
                        .minOf { concert: Concert -> concert.heure_debut }
                }

        planningLayout.setStartTime(earliestTime)

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
            concertFView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.x
                        lastY = event.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val deltaX = event.x - lastX
                        val deltaY = event.y - lastY
                        //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                        ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                        horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                        horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                        lastX = event.x
                        lastY = event.y
                        return@setOnTouchListener true
                    }
                }
                return@setOnTouchListener false
            }

            linearLayout.addView(concertFView)
            if (concerts.size > 1) {
                for (i in concerts.indices-1) {
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


                    concertView.setOnTouchListener { _, event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                lastX = event.x
                                lastY = event.y
                            }
                            MotionEvent.ACTION_MOVE -> {
                                val deltaX = event.x - lastX
                                val deltaY = event.y - lastY
                                //planningLayout.scrollBy(-deltaX.toInt(), -deltaY.toInt())
                                ScrollViewPlanning.scrollBy(0, -deltaY.toInt())
                                horizontalScrollViewPlanning.scrollBy(-deltaX.toInt(), 0)
                                horizontalScrollView.scrollBy(-deltaX.toInt(), 0)
                                lastX = event.x
                                lastY = event.y
                                return@setOnTouchListener true
                            }
                        }
                        return@setOnTouchListener false
                    }

                    linearLayout.addView(concertView)
                }
            }

            planningLayout.addView(linearLayout)
            //sceneLayout.rotation = 90f;





            //sceneLayout.addView(sceneName)
        }


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
