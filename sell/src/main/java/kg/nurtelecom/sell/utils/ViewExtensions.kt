package kg.nurtelecom.sell.utils

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.sell.R

inline fun MenuItem.setupItemActionExpandListener(
    crossinline doOnItemExpand: (item: MenuItem?) -> Boolean = { true },
    crossinline doOnItemCollapse: (item: MenuItem?) -> Boolean = { true }
): MenuItem.OnActionExpandListener {
    val listener = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            return doOnItemExpand.invoke(item)
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            return doOnItemCollapse.invoke(item)
        }
    }
    setOnActionExpandListener(listener)

    return listener
}

inline fun MenuItem.doOnMenuItemExpand(
    crossinline action: (menu: MenuItem?) -> Boolean
) = setupItemActionExpandListener(doOnItemExpand = action)

inline fun MenuItem.doOnMenuItemCollapse(
    crossinline action: (menu: MenuItem?) -> Boolean
) = setupItemActionExpandListener(doOnItemCollapse = action)

fun DrawerLayout.setupActionBarDrawerToggle(
    activity: AppCompatActivity
) {
    val actionBarToggle = ActionBarDrawerToggle(
        activity,
        this,
        R.string.nav_open_drawer,
        R.string.nav_close_drawer
    )
    actionBarToggle.drawerArrowDrawable.color = context.resources.getColor(R.color.white)
    addDrawerListener(actionBarToggle)
    actionBarToggle.syncState()
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

inline fun DrawerLayout.doDrawerStateChanged(
    crossinline action: (newState: Int) -> Unit
) = setupDrawerListener(drawerStateChanged = action)

inline fun DrawerLayout.doOnDrawerClosed(
    crossinline action: (drawerView: View) -> Unit
) = setupDrawerListener(drawerClosed = action)

inline fun DrawerLayout.doOnDrawerOpened(
    crossinline action: (drawerView: View) -> Unit
) = setupDrawerListener(drawerOpened = action)

inline fun DrawerLayout.doOnDrawerSlide(
    crossinline action: (drawerView: View, slideOffset: Float) -> Unit
) = setupDrawerListener(drawerSlide = action)
