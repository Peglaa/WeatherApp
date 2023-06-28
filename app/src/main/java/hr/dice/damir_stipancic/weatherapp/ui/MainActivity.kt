package hr.dice.damir_stipancic.weatherapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hr.dice.damir_stipancic.weatherapp.R
import hr.dice.damir_stipancic.weatherapp.data.preferences.Language
import hr.dice.damir_stipancic.weatherapp.data.preferences.Theme
import hr.dice.damir_stipancic.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesSetup()

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController =
            binding.weatherNavHostFragment.getFragment<NavHostFragment>().navController

        binding.lifecycleOwner = this
        navController.graph.setStartDestination(R.id.currentWeatherFragment)
        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.noLocationFragment,
                R.id.noLocationSavedFragment,
                R.id.noInternetConnectionFragment,
                R.id.errorFragment,
                R.id.locationCheckFragment -> {
                    binding.bottomNavigationBar.visibility = View.GONE
                    binding.toolbar.visibility = View.GONE
                }
                R.id.dailyDetailsWeatherFragment -> {
                    binding.bottomNavigationBar.visibility = View.GONE
                    binding.toolbar.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setHomeButtonEnabled(true)
                }
                else -> {
                    binding.bottomNavigationBar.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    supportActionBar?.setHomeButtonEnabled(false)
                }
            }

            binding.bottomNavigationBar.menu.forEachIndexed { index, it ->
                when (index) {
                    0 -> it.isEnabled = destination.id != R.id.currentWeatherFragment
                    1 -> it.isEnabled = destination.id != R.id.dailyWeatherFragment
                    2 -> it.isEnabled = destination.id != R.id.settingsFragment
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locationCity.collectLatest {
                    supportActionBar?.title = it?.city
                }
            }
        }
    }

    private fun preferencesSetup() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.preferencesFlow.collectLatest {
                    AppCompatDelegate.setDefaultNightMode(
                        when (it.theme) {
                            Theme.DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                            Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                            Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        }
                    )

                    val locale = when (it.language) {
                        Language.CROATIAN -> Locale(Language.CROATIAN.value, "HR")
                        Language.ENGLISH -> Locale.ENGLISH
                        Language.GERMAN -> Locale.GERMAN
                    }
                    val localeList = LocaleListCompat.create(locale)
                    AppCompatDelegate.setApplicationLocales(localeList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.weatherNavHostFragment).navigateUp() ||
            super.onSupportNavigateUp()
    }
}
