package kg.nurtelecom.core.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.extension.toast
import kg.nurtelecom.core.viewmodel.CoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class CoreFragment<VB : ViewBinding, VM : CoreViewModel>(vmClass: KClass<VM>) :
    SimpleFragment<VB>() {

    protected val vm: VM by viewModel(clazz = vmClass)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLiveData()
    }

    open fun subscribeToLiveData() {
        vm.event.observe(viewLifecycleOwner, {
            when (it) {
                is CoreEvent.Notification -> context?.toast(it.message)
            }
        })
    }

}