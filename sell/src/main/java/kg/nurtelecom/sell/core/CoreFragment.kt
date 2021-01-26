package kg.nurtelecom.sell.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.viewmodel.CoreViewModel

abstract class CoreFragment<VB: ViewBinding> : Fragment() {

    private var _vb: VB? = null
    protected val vb get() = _vb!!

    abstract val vm: CoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = createViewBinding(inflater, container)
        return vb.root
    }

    protected abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity.setToolbarTitle(setupToolbar())
        setupViews()
        subscribeToLiveData()
    }

    abstract fun setupToolbar(): Int

    open fun setupViews() {}

    open fun subscribeToLiveData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}