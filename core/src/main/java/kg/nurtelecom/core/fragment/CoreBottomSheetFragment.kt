package kg.nurtelecom.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.extension.toast
import kg.nurtelecom.core.viewmodel.CoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class CoreBottomSheetFragment<VB : ViewBinding, VM : CoreViewModel>(vmClass: KClass<VM>) :
    BottomSheetDialogFragment() {

    protected val vm: VM by viewModel(clazz = vmClass)
    protected val vb: VB by lazy { getBinding() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViews()
        return vb.root
    }

    open fun subscribeToLiveData() {
        vm.event.observe(viewLifecycleOwner, {
            when (it) {
                is CoreEvent.Notification -> context?.toast(it.message)
            }
        })
    }

    abstract fun getBinding(): VB
    open fun setupViews() {}
    open fun navigateToFragment() {}
}