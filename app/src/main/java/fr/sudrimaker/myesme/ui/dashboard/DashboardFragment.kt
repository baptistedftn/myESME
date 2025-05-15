package fr.sudrimaker.myesme.ui.dashboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.model.DashboardItem

class DashboardFragment : Fragment() {

    private lateinit var planningAdapter: PlanningAdapter
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration du RecyclerView pour le Planning (horizontal)
        val recyclerViewPlanning = view.findViewById<RecyclerView>(R.id.recyclerViewPlanning)
        recyclerViewPlanning.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        planningAdapter = PlanningAdapter(getDummyPlanning())
        recyclerViewPlanning.adapter = planningAdapter

        // Configuration du RecyclerView pour les éléments du Dashboard (vertical)
        val recyclerViewDashboard = view.findViewById<RecyclerView>(R.id.recyclerViewDashboard)
        recyclerViewDashboard.layoutManager = LinearLayoutManager(context)
        dashboardAdapter = DashboardAdapter(getDummyDashboardItems())
        recyclerViewDashboard.adapter = dashboardAdapter
    }

    private fun getDummyPlanning(): List<String> {
        return listOf("Cours - Optimisation 1.2", "DS - BDD AMPHI", "TP - IA 3.1")
    }

    private fun getDummyDashboardItems(): List<DashboardItem> {
        return listOf(
            DashboardItem(
                title = "Information 1",
                subtitle = "Détails de l'information 1",
                time = "Maintenant",
                imageUrl = "url_image_info1"
            ),
            DashboardItem(
                title = "Événement 2",
                subtitle = "Description de l'événement 2",
                time = "Plus tard",
                imageUrl = "url_image_event2"
            )
            // Ajoutez d'autres DashboardItem ici
        )
    }
}