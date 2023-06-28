package hr.dice.damir_stipancic.weatherapp.ui.location_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentLocationCheckBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationCheckFragment : Fragment() {

    private lateinit var binding: FragmentLocationCheckBinding
    private val viewModel by viewModel<LocationCheckViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationCheckBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hasSavedLocation.collectLatest {
                    if (it != null)
                        when (it) {
                            false -> {
                                findNavController().navigate(
                                    R.id.action_locationCheckFragment_to_noLocationSavedFragment
                                )
                            }
                            true -> findNavController().navigate(
                                R.id.action_locationCheckFragment_to_currentWeatherFragment
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }
}
