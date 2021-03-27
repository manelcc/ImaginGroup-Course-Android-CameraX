package es.imagingroup.exampleviewmodelhiltdb.presentation.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import es.imagingroup.exampleviewmodelhiltdb.R
import es.imagingroup.exampleviewmodelhiltdb.databinding.ActivityMainBinding
import es.imagingroup.exampleviewmodelhiltdb.presentation.view.fragment.LoginFragment
import es.imagingroup.exampleviewmodelhiltdb.presentation.viewmodel.MainActivityViewModel

class MainActivity: AppCompatActivity() {

    val viewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        supportFragmentManager.beginTransaction().replace(binding.mainRoot.id, LoginFragment()).commitAllowingStateLoss()
    }
}