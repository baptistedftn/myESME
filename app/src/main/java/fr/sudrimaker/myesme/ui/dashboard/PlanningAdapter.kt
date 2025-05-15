// PlanningAdapter.kt
package fr.sudrimaker.myesme.ui.dashboard // Ajustez le package si nécessaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.R

class PlanningAdapter(private val planningList: List<String>) :
    RecyclerView.Adapter<PlanningAdapter.PlanningViewHolder>() {

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPlanningItem: TextView = itemView.findViewById(R.id.textPlanningItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planning_card, parent, false)
        return PlanningViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val item = planningList[position]
        holder.textPlanningItem.text = item
    }

    override fun getItemCount(): Int = planningList.size
}