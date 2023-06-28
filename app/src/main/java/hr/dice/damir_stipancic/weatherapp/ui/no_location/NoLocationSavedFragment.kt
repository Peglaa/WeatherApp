package hr.dice.damir_stipancic.weatherapp.ui.no_location

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentNoLocationSavedBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoLocationSavedFragment : Fragment() {

    private val viewModel by viewModel<NoLocationViewModel>()
    private lateinit var binding: FragmentNoLocationSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoLocationSavedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it)
                    viewModel.getCurrentLocation()
            }

        binding.btnGetCurrentLocation.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigateToCurrentWeather.collectLatest {
                        if (it) {
                            findNavController().navigate(
                                R.id.action_noLocationSavedFragment_to_currentWeatherFragment
                            )
                        }
                    }
                }

                launch {
                    viewModel.navigateToNoLocation.collectLatest {
                        if (it) {
                            findNavController().navigate(
                                R.id.action_noLocationSavedFragment_to_noLocationFragment
                            )
                        }
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
