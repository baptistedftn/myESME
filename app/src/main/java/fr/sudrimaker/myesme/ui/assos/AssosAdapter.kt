package fr.sudrimaker.myesme.ui.assos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.R

class AssosAdapter(private val assos: List<Asso>) :
    RecyclerView.Adapter<AssosAdapter.AssoViewHolder>() {

    inner class AssoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val detailsText: TextView = itemView.findViewById(R.id.detailsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asso, parent, false)
        return AssoViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AssoViewHolder, position: Int) {
        val asso = assos[position]
        holder.nameText.text = asso.name
        holder.detailsText.text =
            "${asso.intitule}"
    }

    override fun getItemCount(): Int = assos.size
}
