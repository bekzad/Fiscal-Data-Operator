package kg.nurtelecom.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun AppCompatActivity.setToolbarTitle(text: String) {
    this.supportActionBar?.setTitle(text)
}

fun AppCompatActivity.setToolbarTitle(resId: Int) {
    this.supportActionBar?.setTitle(resId)
}

fun AppCompatActivity.getCurrentVisibleFragment(): Fragment {
    return supportFragmentManager.fragments.last()
}

fun Date.formatForLocalDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("yyyy/MM/dd - HH:mm", Locale("ru"))
    return sdf.format(this)
}

fun Date.formatForDecoratorDateTimeDefaults(): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return sdf.format(this)
}

fun Date.subtractDays(amount: Int): Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.DATE, -amount)
    return c.time
}

fun Date.formatForCurrentDate(): String {
    return SimpleDateFormat("dd.MM.yy / HH:mm", Locale("ru")).format(this)
}

fun BigDecimal.roundOff(amount: Int): BigDecimal  = setScale(amount, BigDecimal.ROUND_HALF_UP)

fun ProgressBar.setProgressBarColor(color: Int) {
    progressDrawable.setColorFilter(
            resources.getColor(color), android.graphics.PorterDuff.Mode.SRC_IN)
}

inline val Fragment.parentActivity get() = (activity as AppCompatActivity)

inline fun <reified T : Activity> Context.startActivity(noinline extra: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}

inline fun <reified T : Fragment> AppCompatActivity.replaceFragment(
    @IdRes containerId: Int,
    backStack: Boolean = true,
    noinline args: Bundle?.() -> Bundle? = { bundleOf() }
) {
    supportFragmentManager.commit {
        val arguments = Bundle().args()
        if (backStack) replace(containerId, T::class.java, arguments).addToBackStack(null)
        else replace(containerId, T::class.java, arguments)
    }
}