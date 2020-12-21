package kg.nurtelecom.sell.ui.activity

import android.os.Bundle
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToSellFragment()
    }

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)

    private fun navigateToSellFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, SellFragment.newInstance())
            .addToBackStack("kg.nurtelecom.sell.ui.fragment.sell.SellFragment")
            .commit()
    }
}
