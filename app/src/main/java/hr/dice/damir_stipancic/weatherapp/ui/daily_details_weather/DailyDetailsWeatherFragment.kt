package hr.dice.damir_stipancic.weatherapp.ui.daily_details_weather

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
import androidx.recyclerview.widget.LinearLayoutManager
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.exception.ErrorHandler
import hr.dice.damir_stipancic.weatherapp.data.exception.NoInternetConnectionException
import hr.dice.damir_stipancic.weatherapp.data.remote.Resource
import hr.dice.damir_stipancic.weatherapp.databinding.FragmentDailyDetailsWeatherBinding
import hr.dice.damir_stipancic.weatherapp.util.getDayOfWeek
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyDetailsWeatherFragment : Fragment() {

    private val viewModel by viewModel<DailyDetailsWeatherViewModel>()
    private lateinit var binding: FragmentDailyDetailsWeatherBinding
    private val errorHandler: ErrorHandler by inject()
    private val detailsRecyclerAdapter = DailyDetailsRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyDetailsWeatherBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayId: Int = requireArguments().getInt("dayId")
        val recyclerView = binding.rvDailyDetails
        val layoutManager = LinearLayoutManager(requireContext())
        with(recyclerView) {
            this.adapter = detailsRecyclerAdapter
            this.layoutManager = layoutManager
            this.addItemDecoration(
                CustomDividerItemDecoration(
                    requireContext(),
                    layoutManager.orientation
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.dataForUi.collectLatest {
                        detailsRecyclerAdapter.submitList(it)
                    }
                }

                launch {
                    val preferences = viewModel.preferencesFlow.first()
                    viewModel.weatherResponseState.collectLatest {
                        when (it) {
                            is Resource.Loading -> {
                                binding.rvDailyDetails.visibility = View.INVISIBLE
                                binding.progressCircular.visibility = View.VISIBLE
                            }
                            is Resource.Error -> {
                                when (it.throwable) {
                                    is NoInternetConnectionException -> {
                                        findNavController().navigate(
                                            R.id.action_dailyDetailsWeatherFragment_to_noInternetConnectionFragment
                                        )
                                        viewModel.resetWeatherSate()
                                    }
                                    else -> {
                                        val action = DailyDetailsWeatherFragmentDirections
                                            .actionDailyDetailsWeatherFragmentToErrorFragment(
                                                errorHandler.displayErrorMessage(it)
                                            )
                                        findNavController().navigate(action)
                                        viewModel.resetWeatherSate()
                                    }
                                }
                            }
                            is Resource.Success -> {
                                it.data?.let { data ->
                                    viewModel.prepareDataForUi(
                                        data.daily[dayId],
                                        preferences
                                    )

                                    (activity as AppCompatActivity).supportActionBar?.title =
                                        requireContext().resources.getString(
                                            R.string.toolbar_title,
                                            viewModel.locationCity.value,
                                            getDayOfWeek(
                                                data.daily[dayId].date,
                                                requireContext()
                                            ),
                                            data.daily[dayId].formattedDate
                                        )

                                    binding.progressCircular.visibility = View.GONE
                                    binding.rvDailyDetails.visibility = View.VISIBLE
                                }
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
