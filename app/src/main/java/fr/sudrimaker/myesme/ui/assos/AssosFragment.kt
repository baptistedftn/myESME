package fr.sudrimaker.myesme.ui.assos

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentAssosBinding

class AssosFragment : Fragment() {

    private var _binding: FragmentAssosBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var assosAdapter: AssosAdapter

    private val db = FirebaseFirestore.getInstance()

    private fun setupRecyclerView(events: List<AssoEvent>) {
        val sortedEvents = events.sortedBy { it.date }

        recyclerView = binding.assosRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        assosAdapter = AssosAdapter(sortedEvents)
        recyclerView.adapter = assosAdapter

        binding.loader.visibility = View.GONE
        binding.assosRecyclerView.visibility = View.VISIBLE
    }

    private fun fetchEvents(campusFilter: String) {
        binding.loader.visibility = View.VISIBLE
        binding.assosRecyclerView.visibility = View.GONE

        val eventsList = mutableListOf<AssoEvent>()

        val eventsRef = db.collection("assos_events")

        eventsRef.whereEqualTo("campus", campusFilter)
            .orderBy("date")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val event = document.toObject(AssoEvent::class.java)
                    event.asso?.get()?.addOnSuccessListener { assoDoc ->
                        val assoName = assoDoc.getString("name") ?: ""
                        event.assoName = assoName
                        eventsList.add(event)
                        if (eventsList.size == documents.size()) {
                            setupRecyclerView(eventsList)
                        }
                    }?.addOnFailureListener {
                        eventsList.add(event)
                        if (eventsList.size == documents.size()) {
                            setupRecyclerView(eventsList)
                        }
                    }
                }

                if (documents.isEmpty) {
                    binding.loader.visibility = View.GONE
                }
            }
            .addOnFailureListener { _ ->
                binding.loader.visibility = View.GONE
            }
    }

    private fun getFavoriteCampus(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("myESMEprefs", 0)
        return sharedPreferences.getString("favoriteCampus", "Lille") ?: "Lille"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_menu_main)
        }
        val favoriteCampus = getFavoriteCampus()

        fetchEvents(favoriteCampus)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
