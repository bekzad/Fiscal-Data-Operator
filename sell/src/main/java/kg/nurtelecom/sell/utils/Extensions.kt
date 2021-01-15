package kg.nurtelecom.sell.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.math.BigDecimal
import java.math.RoundingMode


fun Fragment.addFragment(@IdRes containerId: Int, fragment: Fragment, backStack: Boolean = false) {
    requireActivity().supportFragmentManager.commit {
        if (backStack) add(containerId, fragment).addToBackStack(null)
        else add(containerId, fragment)
    }
}

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)