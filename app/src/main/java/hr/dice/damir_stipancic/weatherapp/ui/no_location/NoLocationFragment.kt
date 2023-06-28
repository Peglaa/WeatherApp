package hr.dice.damir_stipancic.weatherapp.ui.no_location

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
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentNoLocationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoLocationFragment : Fragment() {

    private val viewModel by viewModel<NoLocationViewModel>()
    private lateinit var binding: FragmentNoLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoLocationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTryAgain.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getCurrentLocation()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.navigateToCurrentWeather.collectLatest {
                        if (it) {
                            findNavController().navigate(
                                R.id.action_noLocationFragment_to_currentWeatherFragment
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
