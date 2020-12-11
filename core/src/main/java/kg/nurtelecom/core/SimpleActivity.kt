package kg.nurtelecom.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class SimpleActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val vb: VB by lazy { getBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        parseDataFromIntent()
        setViews()
    }

    open fun setViews() {}
    open fun parseDataFromIntent() {}

    abstract fun getBinding(): VB
}