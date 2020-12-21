package kg.nurtelecom.core.extension

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kg.nurtelecom.core.Resource

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Activity.handleApiError(
        view: View,
        failure: Resource.Failure,
        retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> view.snackbar(
                "Пожалуйста, проверьте ваше интернет соединение!",
                retry
        )
        failure.errorCode == 401 -> {
            view.snackbar("Вы ввели неверные данные")
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            view.snackbar(error)
        }
    }
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