package fr.sudrimaker.myesme.ui.assos

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class AssoEvent(
    val asso: DocumentReference? = null,
    val campusName: String = "",
    var assoName: String = "",
    val eventName: String = "",
    val date: Timestamp = Timestamp.now(),
    val location: String = "",
    val imageUrl: String = "",
)