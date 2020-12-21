package kg.nurtelecom.core.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class SimpleActivity<VB : ViewBinding> : AppCompatActivity() {

    protected val vb: VB by lazy { getBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        parseDataFromIntent()
        setupViews()
    }

    open fun setupViews() {}
    open fun parseDataFromIntent() {}

    abstract fun getBinding(): VB
}