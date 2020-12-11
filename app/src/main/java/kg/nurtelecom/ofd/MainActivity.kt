package kg.nurtelecom.ofd

import kg.nurtelecom.core.activity.SimpleActivity
import kg.nurtelecom.ofd.databinding.ActivityMainBinding

class MainActivity : SimpleActivity<ActivityMainBinding>() {
    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

}