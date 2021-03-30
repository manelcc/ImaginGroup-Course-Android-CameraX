package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.databinding.HomeFragmentBinding

@AndroidEntryPoint
class HomeFragment:Fragment() {

    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
}