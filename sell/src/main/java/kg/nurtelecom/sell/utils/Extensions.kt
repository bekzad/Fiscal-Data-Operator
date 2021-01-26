package kg.nurtelecom.sell.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.math.BigDecimal
import java.math.RoundingMode


inline fun <reified T : Fragment> AppCompatActivity.addMFragment(
    @IdRes containerId: Int,
    backStack: Boolean = true,
    noinline args: Bundle?.() -> Bundle? = { bundleOf() }
) {
    supportFragmentManager.commit {
        val arguments = Bundle().args()
        if (backStack) add(containerId, T::class.java, arguments).addToBackStack(null)
        else add(containerId, T::class.java, arguments)
    }
}

fun View.disable() {
    isClickable = false
    isFocusable = false
    isLongClickable = false
}

fun AppCompatActivity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)