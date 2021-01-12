package kg.nurtelecom.sell.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    backStack: Boolean = false
) {
    requireActivity().supportFragmentManager.commit {
        if (backStack) replace(containerId, fragment).addToBackStack(null)
        else replace(containerId, fragment)
    }
}

fun Fragment.addFragment(@IdRes containerId: Int, fragment: Fragment, backStack: Boolean = false) {
    requireActivity().supportFragmentManager.commit {
        if (backStack) add(containerId, fragment).addToBackStack(null)
        else add(containerId, fragment)
    }
}

fun Fragment.clearBackStack() {
    val fm = requireActivity().supportFragmentManager
    for (i in 0 until fm.backStackEntryCount) {
        fm.popBackStack()
    }
}

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)