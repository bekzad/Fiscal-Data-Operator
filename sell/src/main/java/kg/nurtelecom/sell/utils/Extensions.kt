package kg.nurtelecom.sell.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import kg.nurtelecom.sell.R
import java.math.BigDecimal
import java.math.RoundingMode


inline fun <reified T : Fragment> AppCompatActivity.addMFragment(
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

inline fun DrawerLayout.setupActionBarDrawerToggle(
    activity: Activity,
    toolbar: Toolbar,
    action: () -> Unit = {}
) {
    val actionBarToggle = ActionBarDrawerToggle(
        activity,
        this,
        toolbar,
        R.string.nav_open_drawer,
        R.string.nav_close_drawer
    )
    actionBarToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
    addDrawerListener(actionBarToggle)
    actionBarToggle.syncState()
    action()
}

inline fun SearchView.setupQueryTextListener(
    crossinline doOnQueryTextSubmit: (query: String) -> Boolean = { false },
    crossinline doOnQueryTextChange: (newText: String) -> Boolean = { true }
): SearchView.OnQueryTextListener {
    val listener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return doOnQueryTextSubmit.invoke(query)
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return doOnQueryTextChange(newText)
        }
    }
    setOnQueryTextListener(listener)

    return listener
}

inline fun SearchView.doOnQueryTextSubmit(
    crossinline action: (query: String) -> Boolean
) = setupQueryTextListener(doOnQueryTextSubmit = action)

inline fun SearchView.doOnQueryTextChange(
    crossinline action: (newText: String) -> Boolean
) = setupQueryTextListener(doOnQueryTextChange = action)

inline fun DrawerLayout.setupDrawerListener(
    crossinline drawerSlide: (drawerView: View, slideOffset: Float) -> Unit = { _, _ -> },
    crossinline drawerStateChanged: (newState: Int) -> Unit = {},
    crossinline drawerOpened: (drawerView: View) -> Unit = {},
    crossinline drawerClosed: (drawerView: View) -> Unit = {}
): DrawerLayout.DrawerListener {
    val listener = object : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            drawerSlide.invoke(drawerView, slideOffset)
        }

        override fun onDrawerOpened(drawerView: View) {
            drawerOpened.invoke(drawerView)
        }

        override fun onDrawerClosed(drawerView: View) {
            drawerClosed(drawerView)
        }

        override fun onDrawerStateChanged(newState: Int) {
            drawerStateChanged(newState)
        }
    }
    addDrawerListener(listener)
    return listener
}

inline fun DrawerLayout.drawerStateChanged(
    crossinline action: (newState: Int) -> Unit
) = setupDrawerListener(drawerStateChanged = action)

inline fun DrawerLayout.drawerClosed(
    crossinline action: (drawerView: View) -> Unit
) = setupDrawerListener(drawerClosed = action)

inline fun DrawerLayout.drawerOpened(
    crossinline action: (drawerView: View) -> Unit
) = setupDrawerListener(drawerOpened = action)

inline fun DrawerLayout.drawerSlide(
    crossinline action: (drawerView: View, slideOffset: Float) -> Unit
) = setupDrawerListener(drawerSlide = action)


// Check whether a given BigDecimal value is a zero or not
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0

fun BigDecimal.roundUp() = this.setScale(2, RoundingMode.CEILING)