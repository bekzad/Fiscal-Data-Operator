package kg.nurtelecom.core.extension

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Activity.handleApiError(
        view: View,
        retry: (() -> Unit)? = null
) {
    view.snackbar("Вы ввели неверные данные")
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}