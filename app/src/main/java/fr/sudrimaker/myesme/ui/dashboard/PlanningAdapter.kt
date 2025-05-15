package fr.sudrimaker.myesme.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.databinding.ItemPlanningCardBinding

class PlanningAdapter(private val planningList: List<String>) :
    RecyclerView.Adapter<PlanningAdapter.PlanningViewHolder>() {

    class PlanningViewHolder(val binding: ItemPlanningCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val binding =
            ItemPlanningCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanningViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val item = planningList[position]
        holder.binding.textPlanningItem.text = item
    }

    override fun getItemCount(): Int = planningList.size
}