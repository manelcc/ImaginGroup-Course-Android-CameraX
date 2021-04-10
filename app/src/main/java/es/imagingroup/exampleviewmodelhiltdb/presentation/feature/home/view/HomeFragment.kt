package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.imagingroup.exampleviewmodelhiltdb.R
import es.imagingroup.exampleviewmodelhiltdb.databinding.HomeFragmentBinding
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.home.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val arguments by navArgs<HomeFragmentArgs>()
    private val viewmodel: HomeViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.setUserHome(arguments.user)
        binding.datastore.setOnClickListener { findNavController().navigate(R.id.to_dataStoreExample) }
        binding.cameraPreview.setOnClickListener { findNavController().navigate(R.id.to_cameraPreview) }

    }


}