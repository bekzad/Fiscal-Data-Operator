package kg.nurtelecom.sell.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.nurtelecom.sell.R
import java.math.BigDecimal
import java.math.RoundingMode


fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    this.supportFragmentManager.beginTransaction().apply {
        addToBackStack(null)
        replace(R.id.container, fragment)
        commit()
    }
}

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)
