package fr.sudrimaker.myesme.ui.assos

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentAssosBinding

class AssosFragment : Fragment() {

    private var _binding: FragmentAssosBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssosAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    val mockEvents = listOf(
        Asso(
            name = "Sudrimaker",
            event = "Atelier robotique",
            date = Timestamp(
                LocalDateTime.of(2025, 5, 12, 14, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
            ),
            campus = "Lille",
            location = "Atelier Sudrimaker",
            imageUrl = "https://picsum.photos/id/1/600/400"
        ),
        Asso(
            name = "BDE",
            event = "Soirée d'intégration",
            date = Timestamp(
                LocalDateTime.of(2025, 10, 20, 21, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
            ),
            campus = "Lille",
            location = "Imprévu",
            imageUrl = "https://picsum.photos/id/117/600/400"
        ),
        Asso(
            name = "BDS",
            event = "Ovalies",
            date = Timestamp(
                LocalDateTime.of(2025, 5, 8, 9, 30)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
            ),
            campus = "Paris, Bordeaux, Lille",
            location = "Stade Marcel Communeau - Beauvais",
            imageUrl = "https://picsum.photos/id/73/600/400"
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_menu_main)
        }

        val recyclerView = binding.assosRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = AssosAdapter(events)
        recyclerView.adapter = AssosAdapter(mockEvents)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}