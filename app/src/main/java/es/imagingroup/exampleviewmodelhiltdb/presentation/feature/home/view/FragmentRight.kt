package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.databinding.FragmentRightBinding
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.viewmodel.HomeViewModel

@AndroidEntryPoint
class FragmentRight:Fragment() {

    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentRightBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRightBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputText = sharedViewModel.inputText
    }
}