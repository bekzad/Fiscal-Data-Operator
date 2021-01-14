package kg.nurtelecom.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun AppCompatActivity.replaceFragment(
    container: Int,
    fragment: Fragment,
    addToBack: Boolean = false,
    backStackTag: String? = null
) {
    if (addToBack)
        supportFragmentManager.beginTransaction().replace(container, fragment)
            .addToBackStack(backStackTag).commit()
    else
        supportFragmentManager.beginTransaction().replace(container, fragment).commit()
}

fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment, args: Bundle?) {
    supportFragmentManager.commit {
        replace(container, fragment::class.java, args)
    }
}

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

fun LocalDateTime.formatForServerDateTimeDefaults() =
    format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))

fun ListView.requestLayoutForChangedDataset() {
    val listAdapter = this.adapter
    listAdapter?.let { adapter ->
        val itemCount = adapter.count

        var totalHeight = 0
        for (position in 0 until itemCount) {
            val item = adapter.getView(position, null, this)
            item.measure(0, 0)

            totalHeight += item.measuredHeight

            val layoutParams = this.layoutParams
            layoutParams.height = totalHeight
            this.requestLayout()
        }
    }
}

val Fragment.parentActivity get() = (activity as AppCompatActivity)

inline fun <reified T : Activity> Context.startActivity(noinline extra: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}

inline fun <reified T : Fragment> AppCompatActivity.replaceFragment(
    @IdRes containerId: Int,
    backStack: Boolean = false,
    noinline args: Bundle?.() -> Bundle? = { bundleOf() }
) {
    supportFragmentManager.commit {
        val arguments = Bundle().args()
        if (backStack) {
            replace(containerId, T::class.java, arguments).addToBackStack(null)
            return
        }
        replace(containerId, T::class.java, arguments)
    }
}
