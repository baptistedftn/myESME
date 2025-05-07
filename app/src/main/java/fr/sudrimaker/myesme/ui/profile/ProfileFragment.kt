package fr.sudrimaker.myesme.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentProfileBinding
import androidx.core.content.edit

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private lateinit var campusSpinner: Spinner
    private var currentCampus: String = ""

    private val coachingModules =
        listOf("Outils Maths", "Maths Fonda", "Anglais", "Système Technique")

    private fun fetchModulesList(onComplete: (List<String>) -> Unit) {
        db.collection("modules").get()
            .addOnSuccessListener { result ->
                val campusList = result.mapNotNull { it.getString("name") }
                onComplete(campusList.sorted())
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erreur de chargement des modules", Toast.LENGTH_SHORT)
                    .show()
                onComplete(emptyList())
            }
    }

    private fun fetchCampusList(onComplete: (List<String>) -> Unit) {
        db.collection("campus").get()
            .addOnSuccessListener { result ->
                val campusList = result.mapNotNull { it.getString("name") }
                onComplete(campusList.sorted())
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erreur de chargement des campus", Toast.LENGTH_SHORT)
                    .show()
                onComplete(emptyList())
            }
    }

    private fun setupCampusSpinner(campusList: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            campusList
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        campusSpinner.adapter = adapter

        val currentCampusPosition = campusList.indexOf(currentCampus)
        if (currentCampusPosition >= 0) {
            campusSpinner.setSelection(currentCampusPosition)
        }

        campusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCampus = campusList[position]
                if (selectedCampus != currentCampus) {
                    saveFavoriteCampus(selectedCampus)
                    currentCampus = selectedCampus
                    Toast.makeText(context, "Campus favori: $selectedCampus", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire
            }
        }
    }

    private fun getFavoriteCampus(): String {
        val sharedPreferences = requireActivity().getSharedPreferences("myESMEprefs", 0)
        return sharedPreferences.getString("favoriteCampus", "Lille") ?: "Lille"
    }

    private fun saveFavoriteCampus(campus: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("myESMEprefs", 0)
        sharedPreferences.edit() {
            putString("favoriteCampus", campus)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_menu_main)
        }
        binding.btnConnect.setOnClickListener {
            val email = binding.email.text;
            val password = binding.password.text;

            binding.email.setText("");
            binding.password.setText("");
            binding.coaching.visibility = View.VISIBLE;
        }
        val coachingDropdownMenu = binding.coachingDropdownMenu;
        val coachingDropdownLayout = binding.coachingDropdownLayout;

        binding.switchCoaching.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                coachingDropdownLayout.visibility = View.VISIBLE;
            } else {
                coachingDropdownLayout.visibility = View.GONE;
            }
        }

        campusSpinner = binding.campusSpinner
        currentCampus = getFavoriteCampus()
        fetchCampusList { campusList ->
            setupCampusSpinner(campusList)
        }

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                coachingModules
            )

        coachingDropdownMenu.setAdapter(adapter)

        coachingDropdownMenu.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Snackbar.make(binding.root, "Sélection : $selectedItem", Snackbar.LENGTH_SHORT).show()
        }

        val imageView = binding.profileImage
        val imageUrl =
            "https://hockeynet.fr/storage/photos_personnes/miniature/70849_721086_photo_id.png"

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.person_24px)
            .error(R.drawable.person_24px)
            .circleCrop()
            .into(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}