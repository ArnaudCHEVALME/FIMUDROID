package com.example.fimudroid.ui.FAQs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.database.models.FAQs

class FAQViewModel (application: Application) : AndroidViewModel(application)  {

    private val faqList = MutableLiveData<List<Question>>()

    init {
        faqList.value = listOf(
            Question(
                "Accès",
                "Le FIMU et ses partenaires proposent diverses solutions de transports. Celles-ci présentent un intérêt pratique, économique et écologique. Mais elles permettent également de gagner du temps dans la circulation ou la recherche de place de stationnement. Ainsi, la bonne humeur peut être à son comble !"
            ),
            Question(
                "Boutique",
                "Goodies, t-shirts, affiches, vous pourrez craquer pour les produits du FIMU en vente à l'espace boutique place de la République ! La compil originale du festivale sera aussi en vente ainsi que le merchandising des artistes."
            ),
            Question("Point info",
                "Le point info pour les festivaliers est situé sur la Place de la République. Des programmes et toutes les informations seront disponibles."),
            Question(
                "What is the airspeed velocity of an unladen swallow?",
                "What do you mean? An African or European swallow?"
            )
        )
    }

    fun getAllFAQs(): LiveData<List<Question>> {
        return faqList
    }
}