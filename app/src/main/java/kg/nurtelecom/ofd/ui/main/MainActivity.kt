package kg.nurtelecom.ofd.ui.main

import android.util.Log
import androidx.core.view.GravityCompat
import kg.nurtelecom.core.activity.SimpleActivity
import kg.nurtelecom.core.extension.getCurrentVisibleFragment
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.ActivityMainBinding
import kg.nurtelecom.ofd.databinding.SideMenuMainBinding
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.ofd.ui.main.fragment.greeting.GreetingFragment
import kg.nurtelecom.sell.utils.setupActionBarDrawerToggle

class MainActivity : SimpleActivity<ActivityMainBinding>() {

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbMain)
        setupNavDrawer()
        replaceFragment<GreetingFragment>(R.id.mainContainer, false)
        vb.tbMain.setNavigationOnClickListener {
            vb.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupNavDrawer() {
        vb.drawerLayout.setupActionBarDrawerToggle(this)
        setupDrawerListener()
    }

    private fun setupDrawerListener() {

        val view = vb.navView.inflateHeaderView(R.layout.side_menu_main)
        val menuView = SideMenuMainBinding.bind(view)
        menuView.btnDrawerClose.setOnClickListener {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuView.mainMenuItemAppInfo.setOnClickListener {
            if (getCurrentVisibleFragment() !is AboutAppFragment) {
                replaceFragment<AboutAppFragment>(R.id.mainContainer)
            }
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        menuView.mainMenuItemMain.setOnClickListener {
            if (getCurrentVisibleFragment() !is GreetingFragment) {
                replaceFragment<GreetingFragment>(R.id.mainContainer)
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

    override fun onBackPressed() {
        if (vb.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
