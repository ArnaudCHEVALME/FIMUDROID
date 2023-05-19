package com.fimu.app.ui.FAQs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FAQViewModel (application: Application) : AndroidViewModel(application)  {

    private val faqList = MutableLiveData<List<Question>>()

    init {
        faqList.value = listOf(
            Question(
                "Quand et où a lieu le FIMU ?",
                "Du 25 au 28 mai 2023 au cœur de la Vieille Ville de Belfort"
            ),
            Question(
                "Aurai-je des navettes et des parkings à disposition ? ",
                "Des <b>navettes gratuites</b> seront mises à disposition. Pour les utiliser, il faudra vous garer sur le parking Techn’hom 3 Chênes, vous rendre à l’arrêt situé devant le Restaurant « La Table » et celle-ci vous déposera directement sur le Pont Clémenceau. <br>" +
                        "<br><b>Horaires de passage :</b><br><br>" +
                        "<b>Vendredi 26 mai :</b> de 18h00 à 01h00<br>" +
                        "<b>Samedi 27 mai :</b> de 13h00 à 01h00<br>" +
                        "<b>Dimanche 28 mai :</b> de 13h00 à 01h00<br><br>" +
                        "Les navettes passeront <b><u>toutes les 30 minutes.</b></u>"
            ),
            Question(
                "Va-t-il y avoir un contrôle de sécurité ?",
                "Oui, des agents seront présents sur chaque entrée du site où une fouille sera faite et dans lieu du festival pour sécuriser le site."
            ),
            Question(
                "Quels sont les objets interdits ?",
                "Bouteilles d’alcool, objets tranchants, substances illicites, drogues, aérosols, bouteilles de parfum."
            ),
            Question(
                "Où puis-je acheter des goodies, des affiches… ?",
                "Cette année, retrouvez les affiches et goodies à la Boutique du FIMU."
            ),
            Question(
                "En quoi le FIMU est responsable ?",
                "Projet mégot recyclé valorisé avec la ville de Belfort et le partenaire, des éco cups sont utilisées."
            ),
            Question(
                "Puis-je emmener mes animaux de compagnie ?",
                "Oui, les chiens et les chats sont acceptés hormis les animaux sauvages."
            ),
            Question(
                "Quelle est l’accessibilité pour les PMR ?",
                "1. Toutes les scènes sont accessibles avec des places réservées ou des plateformes surélevées.<br>" +
                        "2. Des toilettes sont adaptées.<br>" +
                        "3. Des prêts gratuits de fauteuils roulants sont disponibles sur le site.<br>" +
                        "4. Points de recharge pour les fauteuils électriques sur les podiums PMR au niveau des Scènes de l’Arsenal, Corbis et Savoureuse.<br>" +
                        "5. Zone de stationnement réservée.<br>" +
                        "6. Signalétique spécifique.<br>" +
                        "7. Bénévoles formés.<br>" +
                        "8. Activités pour le jeune public dans un espace accessible."
            )
        )
    }

    fun getAllFAQs(): LiveData<List<Question>> {
        return faqList
    }
}