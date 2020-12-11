package kg.nurtelecom.core.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.core.extension.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class CoreActivity<VB : ViewBinding, VM : CoreViewModel>(vmClass: KClass<VM>) :
    SimpleActivity<VB>() {

    protected val vm: VM by viewModel(clazz = vmClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToLiveData()
    }

    open fun subscribeToLiveData() {
        vm.event.observe(this, {
            when (it) {
                is CoreEvent.Notification -> toast(it.message)
            }
        })
    }
}