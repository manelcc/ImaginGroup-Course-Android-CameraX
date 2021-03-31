package es.imagingroup.exampleviewmodelhiltdb.presentation.feature.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import es.app.laliguilla.core.extension.showSnackbar
import es.imagingroup.exampleviewmodelhiltdb.R
import es.imagingroup.exampleviewmodelhiltdb.databinding.LoginFragmentBinding
import es.imagingroup.exampleviewmodelhiltdb.domain.exception.ErrorView
import es.imagingroup.exampleviewmodelhiltdb.domain.model.User
import es.imagingroup.exampleviewmodelhiltdb.presentation.feature.login.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.user.observe(viewLifecycleOwner, this::successLogIn)
        viewModel.loading.observe(viewLifecycleOwner, this::loading)
        viewModel.errorView.observe(viewLifecycleOwner, this::errorView)
    }

    fun successLogIn(user: User) {
        if (user.name.isNotBlank()) {
            findNavController().navigate(LoginFragmentDirections.toHomeFragment(user))
            clearValues()
        }
    }

    private fun clearValues() {
        binding.loginPassword.setText("")
        binding.loginUsername.setText("")
        viewModel.clearValues()
    }

    fun loading(isLoading: Boolean) {
        when (isLoading) {
            true -> binding.loginLoading.visibility = VISIBLE
            false -> binding.loginLoading.visibility = GONE
        }
    }

    fun errorView(errorView: ErrorView) {
        when (errorView) {
            is ErrorView.NetworkException -> showError(errorView.message)
            else -> errorView.message?.let { showError(it) }
        }
    }

    private fun showError(message: String) {
        binding.login.showSnackbar(message)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("UPS algo salio mal")
            .setMessage(message)
            .setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("REINTENTAR") { dialog, which ->
                /*viewModel.logIn(
                    viewModel.userName.value ?: "",
                    viewModel.password.value ?: ""
                )//OPCION 1*/
                viewModel.userName.value?.let { userName ->
                    viewModel.password.value?.let { password ->
                        viewModel.logIn(userName, password)
                    }
                }//OPCION 2
            }
            .show()
    }

    /**
     * 🔥 Without nullifying dataBinding ViewPager2 gets data binding related MEMORY LEAKS
     */
    override fun onDestroyView() {
        super.onDestroyView()
        println("🥵 ${this.javaClass.simpleName} #${this.hashCode()}  onDestroyView()")
        binding.invalidateAll()
    }

}


