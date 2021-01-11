package kg.nurtelecom.sell.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.nurtelecom.sell.R
import java.math.BigDecimal
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    val supportFragmentManager = supportFragmentManager
    val transaction = supportFragmentManager.beginTransaction().apply {
        addToBackStack(null)
        replace(R.id.container, fragment)
        commit()
    }
}

/** not tested yet */
fun View.isVisible(keepBounds: Boolean): ReadWriteProperty<Any, Boolean> =
    object : ReadWriteProperty<Any, Boolean> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            visibility = when {
                value -> View.VISIBLE
                keepBounds -> View.INVISIBLE
                else -> View.GONE
            }
        }

        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
            visibility == View.VISIBLE

    }

// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0