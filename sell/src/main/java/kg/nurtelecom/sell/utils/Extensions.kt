package kg.nurtelecom.sell.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.nurtelecom.sell.R
import java.math.BigDecimal


fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    val supportFragmentManager = supportFragmentManager
    val transaction = supportFragmentManager.beginTransaction().apply {
        addToBackStack(null)
        replace(R.id.container, fragment)
        commit()
    }
}

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0