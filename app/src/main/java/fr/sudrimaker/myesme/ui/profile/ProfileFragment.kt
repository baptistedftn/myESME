package fr.sudrimaker.myesme.ui.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import fr.sudrimaker.myesme.R
import fr.sudrimaker.myesme.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val fruits = listOf("Outils Maths", "Maths Fonda", "Anglais", "Système Technique")

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


        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, fruits)

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