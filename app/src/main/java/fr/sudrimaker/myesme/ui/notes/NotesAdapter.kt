package fr.sudrimaker.myesme.ui.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.R

class NotesAdapter(private val items: List<NotesItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NOTE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is NotesItem.ModuleHeader -> TYPE_HEADER
            is NotesItem.NoteItem -> TYPE_NOTE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_notes_header, parent, false)
                ModuleHeaderViewHolder(view)
            }

            TYPE_NOTE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note, parent, false)
                NoteViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is NotesItem.ModuleHeader -> {
                (holder as ModuleHeaderViewHolder).bind(item)
            }

            is NotesItem.NoteItem -> {
                (holder as NoteViewHolder).bind(item.note)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ModuleHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moduleNameText: TextView = itemView.findViewById(R.id.moduleNameText)
        private val moduleAverageText: TextView = itemView.findViewById(R.id.moduleAverageText)

        @SuppressLint("SetTextI18n")
        fun bind(header: NotesItem.ModuleHeader) {
            moduleNameText.text = header.moduleName
            moduleAverageText.text = "Moyenne: ${"%.2f".format(header.average)}/20"
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moduleText: TextView = itemView.findViewById(R.id.moduleText)
        private val detailsText: TextView = itemView.findViewById(R.id.detailsText)

        @SuppressLint("SetTextI18n")
        fun bind(note: Note) {
            moduleText.text = note.intitule
            detailsText.text =
                "${"%.2f".format(note.note)}/20 • Coeff ${note.coefficient}"
        }
    }
}