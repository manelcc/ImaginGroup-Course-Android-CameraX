package es.imagingroup.exampleviewmodelhiltdb.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.R
import es.imagingroup.exampleviewmodelhiltdb.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setToolbar(binding)
        binding.lifecycleOwner = this
    }

    private fun setToolbar(binding: ActivityMainBinding) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_root)
        navHostFragment?.run {
            val navController = navHostFragment.findNavController()
            appBarConfiguration = AppBarConfiguration(navController.graph)
            binding.componentToolbar.setupWithNavController(navController, appBarConfiguration)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(findNavController(R.id.nav_root), appBarConfiguration)
    }
}