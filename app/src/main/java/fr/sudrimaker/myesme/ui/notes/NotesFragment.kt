package fr.sudrimaker.myesme.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter

    private val notes = listOf(
        Note("Outils Maths", 15.5, 3, "DS"),
        Note("Maths Fonda", 12.0, 4, "TP"),
        Note("Linux", 18.25, 2, "Eval"),
        Note("Optimisation", 9.75, 3, "DS"),
        Note("Électronique", 14.0, 1, "TD"),
        Note("Maths Fonda", 16.5, 5, "TP"),
        Note("Linux", 11.0, 2, "DS"),
        Note("Optimisation", 17.8, 4, "Eval"),
        Note("Électronique", 13.4, 2, "TP"),
        Note("Outils Maths", 19.0, 1, "TD"),
        Note("Maths Fonda", 8.5, 3, "DS"),
        Note("Linux", 10.25, 2, "Eval"),
        Note("Optimisation", 12.6, 3, "TD"),
        Note("Électronique", 15.75, 2, "DS"),
        Note("Outils Maths", 16.2, 4, "TP"),
        Note("Maths Fonda", 9.0, 1, "Eval"),
        Note("Linux", 13.0, 3, "DS"),
        Note("Optimisation", 18.5, 5, "TP"),
        Note("Électronique", 11.8, 2, "Eval"),
        Note("Outils Maths", 17.3, 3, "DS")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_menu_main)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = NotesAdapter(notes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}