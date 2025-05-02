package fr.sudrimaker.myesme.ui.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.R

class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moduleText: TextView = itemView.findViewById(R.id.moduleText)
        val detailsText: TextView = itemView.findViewById(R.id.detailsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.moduleText.text = note.module
        holder.detailsText.text =
            "${note.intitule} • ${"%.2f".format(note.note)}/20 • Coeff ${note.coefficient}"
    }

    override fun getItemCount(): Int = notes.size
}
