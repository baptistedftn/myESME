package fr.sudrimaker.myesme.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.ItemDashboardCardBinding

class DashboardAdapter(private val dashboardList: List<DashboardItem>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(val binding: ItemDashboardCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding =
            ItemDashboardCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = dashboardList[position]
        with(holder.binding) {
            textViewTitle.text = item.title
            textViewSubtitle.text = item.subtitle
            textViewTime.text = item.time

            // Afficher l'emplacement si disponible
            if (item.location.isNotEmpty()) {
                textViewLocation.visibility = View.VISIBLE
                textViewLocation.text = item.location
            } else {
                textViewLocation.visibility = View.GONE
            }

            Glide.with(root.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.arrow_back_24px)
                .error(R.drawable.chevron_right_24px)
                .into(imageViewDashboard)
        }
    }

    override fun getItemCount(): Int = dashboardList.size
}