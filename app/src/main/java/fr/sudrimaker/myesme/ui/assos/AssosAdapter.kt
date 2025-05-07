package fr.sudrimaker.myesme.ui.assos

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.sudrimaker.myesme.R
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class AssosAdapter(private val assos: List<AssoEvent>) :
    RecyclerView.Adapter<AssosAdapter.AssoViewHolder>() {

    inner class AssoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textAssociation: TextView = itemView.findViewById(R.id.textAssociation)
        val textEventTitle: TextView = itemView.findViewById(R.id.textEventTitle)
        val textDateTime: TextView = itemView.findViewById(R.id.textDateTime)
        val textLocation: TextView = itemView.findViewById(R.id.textLocation)
        val textCampus: TextView = itemView.findViewById(R.id.textCampus)
        val imageAsso: ImageView = itemView.findViewById(R.id.imageEvent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asso, parent, false)
        return AssoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AssoViewHolder, position: Int) {
        val asso = assos[position]
        holder.textAssociation.text = asso.assoName
        holder.textCampus.text = asso.campusName
        holder.textEventTitle.text = asso.eventName

        Glide.with(holder.itemView.context)
            .load(asso.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(holder.imageAsso)

        val formattedDate = formateDate(asso)

        holder.textDateTime.text = formattedDate
        holder.textLocation.text = "\uD83D\uDCCD ${asso.location}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formateDate(asso: AssoEvent): String {
        val localDateTime = asso.date.toDate()
            .toInstant()
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime()
        if (localDateTime != null) {
            if (localDateTime.hour == 0 && localDateTime.second == 0) {
                val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("fr"))

                val formattedDate = localDateTime.format(formatter)?.let {
                    "📅 $it"
                } ?: "📅 Date inconnue"
                return formattedDate
            }
        }
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy · 🕓 HH'h'mm", Locale("fr"))

        val formattedDate = localDateTime?.format(formatter)?.let {
            "📅 $it"
        } ?: "📅 Date inconnue"
        return formattedDate
    }

    override fun getItemCount(): Int = assos.size
}
