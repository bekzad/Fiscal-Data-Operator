package kg.nurtelecom.core.extension

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment, addToBack: Boolean = false, backStackTag: String? = null) {
    if (addToBack)
        supportFragmentManager.beginTransaction().replace(container, fragment).addToBackStack(backStackTag).commit()
    else
        supportFragmentManager.beginTransaction().replace(container, fragment).commit()
}

fun text(editText: EditText) = editText.text.toString().trim()

fun AppCompatActivity.setToolbarTitle(text: String) {
    this.supportActionBar?.setTitle(text)
}

fun AppCompatActivity.setToolbarTitle(resId: Int) {
    this.supportActionBar?.setTitle(resId)
}

fun AppCompatActivity.getCurrentVisibleFragment() : Fragment{
    return supportFragmentManager.fragments.last()
}
