package kg.nurtelecom.sell.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


fun AppCompatActivity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0
fun BigDecimal.isGreaterThan(another: BigDecimal) = this.compareTo(another) == 1
fun BigDecimal.isLessThan(another: BigDecimal) = this.compareTo(another) == -1
fun BigDecimal.isEqualTo(another: BigDecimal) = this.compareTo(another) == 0
fun BigDecimal.isGreaterThanOrEqualTo(another: BigDecimal) = this.isGreaterThan(another) || this.isEqualTo(another)
fun BigDecimal.isLessThanOrEqualTo(another: BigDecimal) = this.isLessThan(another) || this.isEqualTo(another)

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.HALF_UP)