package fr.sudrimaker.myesme.ui.assos

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Asso(
    val name: String = "",
    val event: String = "",
    val date: Timestamp? = null,
    val location: String = "",
    val campus: String = "",
    val imageUrl: String = ""
)