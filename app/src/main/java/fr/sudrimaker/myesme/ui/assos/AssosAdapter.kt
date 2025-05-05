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

class AssosAdapter(private val assos: List<Asso>) :
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
        holder.textAssociation.text = asso.name
        holder.textCampus.text = asso.campus
        holder.textEventTitle.text = asso.event

        Glide.with(holder.itemView.context)
            .load(asso.imageUrl)
//            .placeholder(R.drawable.placeholder) // image temporaire
//            .error(R.drawable.error_image)       // si l'image échoue
            .into(holder.imageAsso)

        val localDateTime = asso.date?.toDate()
            ?.toInstant()
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy · 🕓 HH'h'mm", Locale("fr"))

        val formattedDate = localDateTime?.format(formatter)?.let {
            "📅 $it"
        } ?: "📅 Date inconnue"

        holder.textDateTime.text = formattedDate
        holder.textLocation.text = "\uD83D\uDCCD ${asso.location}"
    }

    override fun getItemCount(): Int = assos.size
}
