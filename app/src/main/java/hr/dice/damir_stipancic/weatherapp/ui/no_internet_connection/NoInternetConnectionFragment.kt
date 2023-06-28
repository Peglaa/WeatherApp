package hr.dice.damir_stipancic.weatherapp.ui.no_internet_connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentNoInternetConnectionBinding
import hr.dice.damir_stipancic.weatherapp.util.hasInternetConnection

class NoInternetConnectionFragment : Fragment() {

    private lateinit var binding: FragmentNoInternetConnectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoInternetConnectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTryAgain.setOnClickListener {
            if (hasInternetConnection(requireContext())) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
