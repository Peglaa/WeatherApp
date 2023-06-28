package hr.dice.damir_stipancic.weatherapp.ui.current_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.exception.ErrorHandler
import hr.dice.damir_stipancic.weatherapp.data.exception.NoInternetConnectionException
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentCurrentWeatherBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentWeatherFragment : Fragment() {

    private val viewModel by viewModel<CurrentWeatherViewModel>()
    private lateinit var binding: FragmentCurrentWeatherBinding
    private val errorHandler: ErrorHandler by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.preferencesFlow.collectLatest {
                        binding.preferences = it
                    }
                }

                launch {
                    viewModel.weatherResponseState.collectLatest {
                        when (it) {
                            is Resource.Loading -> {
                                binding.apply {
                                    cardView.visibility = View.INVISIBLE
                                    tvWeather.visibility = View.INVISIBLE
                                    tvTemperature.visibility = View.INVISIBLE
                                    ivWeather.visibility = View.INVISIBLE
                                    progressCircular.visibility = View.VISIBLE
                                }
                                (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(
                                    false
                                )
                            }
                            is Resource.Error -> {
                                when (it.throwable) {
                                    is NoInternetConnectionException -> {
                                        findNavController().navigate(
                                            R.id.action_currentWeatherFragment_to_noInternetConnectionFragment
                                        )
                                        viewModel.resetWeatherState()
                                    }
                                    else -> {
                                        val action = CurrentWeatherFragmentDirections
                                            .actionCurrentWeatherFragmentToErrorFragment(
                                                errorHandler.displayErrorMessage(it)
                                            )
                                        findNavController().navigate(action)
                                        viewModel.resetWeatherState()
                                    }
                                }
                            }

                            is Resource.Success -> {
                                binding.apply {
                                    currentWeather = it.data
                                    cardView.visibility = View.VISIBLE
                                    tvWeather.visibility = View.VISIBLE
                                    tvTemperature.visibility = View.VISIBLE
                                    ivWeather.visibility = View.VISIBLE
                                    progressCircular.visibility = View.GONE
                                }
                                (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(
                                    true
                                )
                            }
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
