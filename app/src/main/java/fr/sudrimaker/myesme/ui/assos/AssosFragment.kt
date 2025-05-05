package fr.sudrimaker.myesme.ui.assos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentAssosBinding

class AssosFragment : Fragment() {

    private var _binding: FragmentAssosBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssosAdapter

    private val assos = listOf(
        Asso("Sudrimaker", 15.5, 3, "Concours"),
        Asso("BDS", 15.5, 3, "Match"),
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_menu_main)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.assosRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AssosAdapter(assos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}