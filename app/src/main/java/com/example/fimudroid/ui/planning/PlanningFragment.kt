package com.example.fimudroid.ui.planning

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.network.retrofit
import com.example.fimudroid.util.OnItemClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
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


    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_planning, container, false)
        planningLayout = root.findViewById(R.id.planning_vertical_linear_layout)
        toggleButtonGroup = root.findViewById(R.id.toggleButton)
        catsLegend = root.findViewById(R.id.cat_legend_linear_layout)

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
        return root
    }

    private fun initBtns() {
        for (d in dates) {
            val button = MaterialButton(requireContext())
            button.id = View.generateViewId()
            button.text = d
            button.setOnClickListener {
                concertsByScene = concertsByDateByScene[d]!!
                initPlanningView()
            }
            toggleButtonGroup.addView(button)
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
/*            sceneName.setBackgroundColor(Color.parseColor("#0000FF"))
            linearLayout.setBackgroundColor(Color.parseColor("#FF0000"))*/
            linearLayout.addView(sceneName)

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
                for (i in concerts.indices-1) {
                    val blankP = View(linearLayout.context)
                    val concertP = concerts[i]
                    val concertN = concerts[i + 1]

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
                    linearLayout.addView(concertView)
                }
            }

            planningLayout.addView(linearLayout)
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
