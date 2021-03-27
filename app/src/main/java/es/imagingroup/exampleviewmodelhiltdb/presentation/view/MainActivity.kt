package es.imagingroup.exampleviewmodelhiltdb.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.imagingroup.exampleviewmodelhiltdb.presentation.viewmodel.MainActivityViewModel

class MainActivity: AppCompatActivity() {

    val viewModel:MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.print("Hello viewmodel")


    }
}