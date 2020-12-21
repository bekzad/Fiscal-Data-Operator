package kg.nurtelecom.sell.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.nurtelecom.sell.R

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    val supportFragmentManager = supportFragmentManager
    val transaction = supportFragmentManager.beginTransaction().apply {
        addToBackStack(null)
        replace(R.id.container, fragment)
        commit()
    }
}