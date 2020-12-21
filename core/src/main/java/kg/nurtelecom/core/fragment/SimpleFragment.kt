package kg.nurtelecom.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class SimpleFragment<VB : ViewBinding> : Fragment() {

    protected val vb: VB by lazy { getBinding() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViews()
        return vb.root
    }

    abstract fun getBinding(): VB
    open fun setupViews() {}
    open fun navigateToFragment() {}



}