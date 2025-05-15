package fr.sudrimaker.myesme.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import fr.sudrimaker.myesme.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var planningAdapter: PlanningAdapter
    private lateinit var dashboardAdapter: DashboardAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPlanningRecyclerView()
        setupDashboardRecyclerView()
        loadEventsFromFirestore()
    }

    private fun setupPlanningRecyclerView() {
        binding.recyclerViewPlanning.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            planningAdapter = PlanningAdapter(getDummyPlanning())
            adapter = planningAdapter
        }
    }

    private fun setupDashboardRecyclerView() {
        binding.recyclerViewDashboard.apply {
            layoutManager = LinearLayoutManager(context)
            dashboardAdapter = DashboardAdapter(emptyList()) // Commence avec une liste vide
            adapter = dashboardAdapter
        }
    }

    private fun loadEventsFromFirestore() {
        val favoriteCampus = getFavoriteCampus()

        db.collection("events")
            .whereEqualTo("campus", favoriteCampus)
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val eventsList = mutableListOf<DashboardItem>()

                for (document in documents) {
                    try {
                        val name = document.getString("name") ?: ""
                        val description = document.getString("description") ?: ""
                        val location = document.getString("location") ?: ""
                        val imageUrl = document.getString("imageUrl") ?: ""
                        val timestamp = document.getTimestamp("date")

                        val formattedTime = if (timestamp != null) {
                            formatTimestamp(timestamp)
                        } else {
                            "Date inconnue"
                        }

                        eventsList.add(
                            DashboardItem(
                                title = name,
                                subtitle = description,
                                time = formattedTime,
                                imageUrl = imageUrl,
                                location = location,
                                description = description,
                                date = timestamp
                            )
                        )
                    } catch (e: Exception) {
                        Log.e("dashboard", "Erreur lors du parsing d'un document: ${e.message}")
                    }
                }

                // Mettre à jour l'adaptateur avec les données
                if (eventsList.isNotEmpty()) {
                    dashboardAdapter = DashboardAdapter(eventsList)
                    binding.recyclerViewDashboard.adapter = dashboardAdapter
                } else {
                    // Si aucun événement n'est trouvé, afficher des données de test
                    dashboardAdapter = DashboardAdapter(getDummyDashboardItems())
                    binding.recyclerViewDashboard.adapter = dashboardAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "dashboard",
                    "Erreur lors de la récupération des documents: ${exception.message}"
                )
                // En cas d'erreur, afficher des données de test
                dashboardAdapter = DashboardAdapter(getDummyDashboardItems())
                binding.recyclerViewDashboard.adapter = dashboardAdapter
            }
    }

    private fun formatTimestamp(timestamp: Timestamp): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp.seconds * 1000))
    }

    private fun getFavoriteCampus(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("myESMEprefs", 0)
        return sharedPreferences.getString("favoriteCampus", "Lille") ?: "Lille"
    }

    private fun getDummyPlanning(): List<String> {
        return listOf("Cours - Optimisation 1.2", "DS - BDD AMPHI", "TP - IA 3.1")
    }

    private fun getDummyDashboardItems(): List<DashboardItem> {
        return listOf(
            DashboardItem(
                title = "Événement 2",
                subtitle = "Description de l'événement 2",
                time = "Plus tard",
                imageUrl = "https://picsum.photos/200/301"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}