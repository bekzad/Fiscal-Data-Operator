package kg.nurtelecom.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kg.nurtelecom.core.R

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment, addToBack: Boolean = false, backStackTag: String? = null) {
    if (addToBack)
        supportFragmentManager.beginTransaction().replace(container, fragment).addToBackStack(backStackTag).commit()
    else
        supportFragmentManager.beginTransaction().replace(container, fragment).commit()
}

fun EditText.getTrimmedText() = text.toString().trim()

fun AppCompatActivity.setToolbarTitle(text: String) {
    this.supportActionBar?.setTitle(text)
}

fun AppCompatActivity.setToolbarTitle(resId: Int) {
    this.supportActionBar?.setTitle(resId)
}

fun AppCompatActivity.getCurrentVisibleFragment() : Fragment{
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

fun LocalDateTime.formatForServerDateTimeDefaults() = format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))

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

inline fun <reified T : Activity> Context.startActivity(noinline extra: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}