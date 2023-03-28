import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Concert
import com.example.fimudroid.network.models.Scene
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanningViewModel : ViewModel() {

    private var concerts: List<Concert> = emptyList()
    var concertsByDateByScene: Map<String, Map<Scene, List<Concert>>> = emptyMap()

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

     fun loadConcerts() {
        viewModelScope.launch(Dispatchers.IO) {
            concerts = api.getConcerts().data
            concertsByDateByScene = concerts
                .groupBy { concert -> concert.date_debut }
                .mapValues { (_, concerts) ->
                    concerts.groupBy { concert -> concert.scene }
                }
        }
    }
}