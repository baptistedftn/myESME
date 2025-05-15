// DashboardAdapter.kt
package fr.sudrimaker.myesme.ui.dashboard // Ajustez le package si nécessaire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.model.DashboardItem

class DashboardAdapter(private val dashboardList: List<DashboardItem>) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dashboardImage: ImageView = itemView.findViewById(R.id.imageViewDashboard)
        val dashboardTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val dashboardSubtitle: TextView = itemView.findViewById(R.id.textViewSubtitle)
        val dashboardTime: TextView = itemView.findViewById(R.id.textViewTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_card, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = dashboardList[position]
        holder.dashboardTitle.text = item.title
        holder.dashboardSubtitle.text = item.subtitle
        holder.dashboardTime.text = item.time

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.arrow_back_24px)
            .error(R.drawable.chevron_right_24px)
            .into(holder.dashboardImage)
    }

    override fun getItemCount(): Int = dashboardList.size
}
 