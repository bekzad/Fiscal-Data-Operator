package kg.nurtelecom.ofd.ui.main

import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.ActivityMainBinding
import kg.nurtelecom.ofd.databinding.SideMenuMainBinding
import kg.nurtelecom.ofd.ui.aboutapp.AboutAppFragment
import kg.nurtelecom.ofd.ui.main.fragment.greeting.GreetingFragment

class MainActivity : CoreActivity<ActivityMainBinding, MainVM>(MainVM::class) {

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbMain)
        setupNavDrawer()
        replaceFragment(R.id.mainContainer, GreetingFragment.newInstance())
    }

    private fun setupNavDrawer() {
        val actionBarToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,vb.drawerLayout, vb.tbMain,R.string.nav_open_drawer, R.string.nav_close_drawer )
        actionBarToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        vb.drawerLayout.addDrawerListener(actionBarToggle)
        setupDrawerListener()
        actionBarToggle.syncState()
    }

    private fun setupDrawerListener() {
        vb.drawerLayout.addDrawerListener(drawerListener())
        val view = vb.navView.getHeaderView(0)
        val v = SideMenuMainBinding.bind(view)
        v.mainMenuItemAppInfo.setOnClickListener {
            replaceFragment(R.id.mainContainer, AboutAppFragment.newInstance(), true)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.mainMenuItemMain.setOnClickListener {
            replaceFragment(R.id.mainContainer, GreetingFragment.newInstance())
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun drawerListener(): DrawerLayout.DrawerListener {
        return object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerOpened(drawerView: View) {
                setToolbarTitle(resources.getString(R.string.menu))
            }
        }
    }

    override fun onBackPressed() {
        if (vb.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
