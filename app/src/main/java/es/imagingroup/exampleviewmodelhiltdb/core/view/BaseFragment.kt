package es.imagingroup.exampleviewmodelhiltdb.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController



abstract class BaseFragment <DB : ViewDataBinding>:Fragment(){

    protected abstract val baseViewModel: BaseViewModel?
    protected lateinit var dataBinding: DB
    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun setBindingLayout()
    abstract fun initViewModels()
    abstract fun observerViewModels()
    open fun eventsOnClick(){}
    open fun initViews(){}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        this.dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsOnClick()
        initViews()
        initViewModels()
        observerViewModels()
        setBindingLayout()
        baseViewModel?.let {
            observerLoading()
            observerErrorView()
        }
    }

    private fun observerErrorView() {


    }

    private fun observerLoading() {


    }

    /**
     * Prevent IllegalArgumentException cannot be found from the current destination Destination
     */
    protected fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

    fun navigate(destination: Int) = with(findNavController()) {
        navigate(destination)
    }

    /**
     * 🔥 Without nullifying dataBinding ViewPager2 gets data binding related MEMORY LEAKS
     */
    override fun onDestroyView() {
        super.onDestroyView()
        println("🥵 ${this.javaClass.simpleName} #${this.hashCode()}  onDestroyView()")
        dataBinding.invalidateAll()
    }

}