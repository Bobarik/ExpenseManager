package com.gmail.vlaskorobogatov.expensemanager.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.ActivityMainBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = fragment.findNavController()

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setupWithNavController(navController, binding.activityLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, null)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setAppLocale()
    }

    fun setAppLocale() {
        val locale = viewModel.readLocale()
        if (locale != resources.configuration.locales[0]) {
            resources.configuration.setLocale(locale)
            resources.updateConfiguration(resources.configuration,
                resources.displayMetrics)
            super.onRestart()
            val refresh = Intent(this, MainActivity::class.java)
            startActivity(refresh)
            finish()
        }
    }
}