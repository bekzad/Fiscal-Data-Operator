package kg.nurtelecom.ofd.ui.main

import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.getCurrentVisibleFragment
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.menu.DrawerListener
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.ActivityMainBinding
import kg.nurtelecom.ofd.databinding.SideMenuMainBinding
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.ofd.ui.main.fragment.greeting.GreetingFragment
import kg.nurtelecom.sell.ui.fragment.credit.CreditListFragment

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
        vb.drawerLayout.addDrawerListener(actionBarToggle)
        setupDrawerListener()
        actionBarToggle.syncState()
        vb.tbMain.setNavigationIcon(R.drawable.ic_baseline_menu_24)
    }

    private fun setupDrawerListener() {
        vb.drawerLayout.addDrawerListener(drawerListener())
        val view = vb.navView.getHeaderView(0)
        val menuView = SideMenuMainBinding.bind(view)
        menuView.mainMenuItemAppInfo.setOnClickListener {
            if (getCurrentVisibleFragment() !is AboutAppFragment) {
                replaceFragment(R.id.mainContainer, AboutAppFragment.newInstance(), true)
            }
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuView.mainMenuItemMain.setOnClickListener {
            if (getCurrentVisibleFragment() !is GreetingFragment) {
                replaceFragment(R.id.mainContainer, GreetingFragment.newInstance())
            }
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuView.btnTestXReport.setOnClickListener {
            Log.i("TAG", "Clicked on button test X report")
        }
        menuView.btnTestZReport.setOnClickListener {
            Log.i("TAG", "Clicked on button test Z report")
        }
    }

    private fun drawerListener(): DrawerLayout.DrawerListener {
        return object : DrawerListener() {

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setToolbarTitle(resources.getString(R.string.menu))
                vb.tbMain.setNavigationIcon(R.drawable.ic_baseline_close_24)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                vb.tbMain.setNavigationIcon(R.drawable.ic_baseline_menu_24)
                when (getCurrentVisibleFragment()) {
                    is GreetingFragment -> setToolbarTitle(R.string.main_greeting)
                    is AboutAppFragment -> setToolbarTitle(R.string.info_about_app)
                }
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
