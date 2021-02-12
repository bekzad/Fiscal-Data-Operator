package kg.nurtelecom.sell.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import java.math.BigDecimal
import java.math.RoundingMode


inline fun <reified T : Fragment> AppCompatActivity.customAddFragment(
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
fun BigDecimal.isGreaterThan(another: BigDecimal) = this.compareTo(another) == 1
fun BigDecimal.isLessThan(another: BigDecimal) = this.compareTo(another) == -1
fun BigDecimal.isEqualTo(another: BigDecimal) = this.compareTo(another) == 0
fun BigDecimal.isGreaterThanOrEqualTo(another: BigDecimal) = this.isGreaterThan(another) || this.isEqualTo(another)
fun BigDecimal.isLessThanOrEqualTo(another: BigDecimal) = this.isLessThan(another) || this.isEqualTo(another)

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)

@ExperimentalCoroutinesApi
inline fun <reified T> SharedPreferences.observeKey(key: String, default: T): Flow<T> {
    val flow = MutableStateFlow(getItem(key, default))

    val listener = SharedPreferences.OnSharedPreferenceChangeListener {_, k ->
        if (key == k) {
            flow.value = getItem(key, default)!!
        }
    }

    registerOnSharedPreferenceChangeListener(listener)

    return flow.onCompletion { unregisterOnSharedPreferenceChangeListener(listener) }
}

inline fun <reified T> SharedPreferences.getItem(key: String, default: T): T {
    @Suppress("UNCHECKED_CAST")
    return when (default) {
        is String -> getString(key, default) as T
        is Int -> getInt(key, default) as T
        is Long -> getLong(key, default) as T
        is Boolean -> getBoolean(key, default) as T
        is Float -> getFloat(key, default) as T
        is Set<*> -> getStringSet(key, default as? Set<String>) as T
        is MutableSet<*> -> getStringSet(key, default as? MutableSet<String>) as T
        else -> throw IllegalArgumentException("generic type not handle ${T::class.java.name}")
    }
}