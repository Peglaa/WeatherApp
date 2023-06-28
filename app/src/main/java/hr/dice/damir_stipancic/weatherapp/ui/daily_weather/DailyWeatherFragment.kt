package hr.dice.damir_stipancic.weatherapp.ui.daily_weather

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
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentDailyWeatherBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyWeatherFragment : Fragment() {

    private val viewModel by viewModel<DailyWeatherViewModel>()
    private lateinit var binding: FragmentDailyWeatherBinding
    private val errorHandler: ErrorHandler by inject()
    private val recyclerAdapter = DailyWeatherPagerAdapter { position ->
        findNavController()
            .navigate(
                DailyWeatherFragmentDirections
                    .actionDailyWeatherFragmentToDailyDetailsWeatherFragment(
                        position
                    )
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDailyWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.adapter = recyclerAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val indicators = binding.dotsIndicator
        val viewPager = binding.vp2DailyWeather
        viewPager.adapter = recyclerAdapter
        indicators.attachTo(viewPager)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.dataForUi.collectLatest {
                        recyclerAdapter.submitList(it)
                    }
                }

                launch {
                    val preferences = viewModel.preferencesFlow.first()
                    viewModel.weatherResponseState.collectLatest {
                        when (it) {
                            is Resource.Loading -> {
                                binding.vp2DailyWeather.visibility = View.INVISIBLE
                                binding.dotsIndicator.visibility = View.INVISIBLE
                                binding.progressCircular.visibility = View.VISIBLE
                            }
                            is Resource.Error -> {
                                when (it.throwable) {
                                    is NoInternetConnectionException -> {
                                        findNavController().navigate(
                                            R.id.action_dailyWeatherFragment_to_noInternetConnectionFragment
                                        )
                                        viewModel.resetWeatherStatus()
                                    }
                                    else -> {
                                        val action = DailyWeatherFragmentDirections
                                            .actionDailyWeatherFragmentToErrorFragment(
                                                errorHandler.displayErrorMessage(it)
                                            )
                                        findNavController().navigate(action)
                                        viewModel.resetWeatherStatus()
                                    }
                                }
                            }

                            is Resource.Success -> {
                                viewModel.prepareDataForUi(
                                    it.data?.daily,
                                    preferences
                                )

                                binding.progressCircular.visibility = View.GONE
                                binding.vp2DailyWeather.visibility = View.VISIBLE
                                binding.dotsIndicator.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = viewModel.locationCity.value
    }
}
