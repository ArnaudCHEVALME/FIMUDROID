import com.example.fimudroid.database.models.TypeNews

data class NewsWithType(
    val id: Int,
    val contenu: String?,
    val date_envoi: String?,
    val titre: String?,
    val type: TypeNews
)