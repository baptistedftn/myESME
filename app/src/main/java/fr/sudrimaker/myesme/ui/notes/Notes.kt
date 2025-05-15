package fr.sudrimaker.myesme.ui.notes

data class Note(
    val module: String,
    val note: Double,
    val coefficient: Int,
    val intitule: String
)

sealed class NotesItem {
    data class ModuleHeader(val moduleName: String, val average: Double) : NotesItem()
    data class NoteItem(val note: Note) : NotesItem()
}