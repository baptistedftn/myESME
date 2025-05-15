package fr.sudrimaker.myesme.ui.dashboard

data class DashboardItem(
    val title: String,
    val subtitle: String,
    val time: String,
    val imageUrl: String,
    val location: String = "",
    val description: String = "",
    val date: com.google.firebase.Timestamp? = null
)