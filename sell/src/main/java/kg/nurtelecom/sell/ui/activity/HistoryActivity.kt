package kg.nurtelecom.sell.ui.activity

import android.content.Context
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.getCurrentVisibleFragment
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.extension.startActivity
import kg.nurtelecom.core.menu.DrawerListener
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivityHistoryBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.bottom_sheet.BottomSheetFragment
import kg.nurtelecom.sell.ui.fragment.history.HistoryFragment
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryActivity :
    CoreActivity<ActivityHistoryBinding, HistoryViewModel>(HistoryViewModel::class) {

    override fun getBinding(): ActivityHistoryBinding =
        ActivityHistoryBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbHistory)
        setupNavDrawer()
        replaceFragment<HistoryFragment>(R.id.sell_container, false)
    }

    private fun setupNavDrawer() {
        val actionBarToggle = ActionBarDrawerToggle(
            this,
            vb.drawerLayout,
            vb.tbHistory,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        actionBarToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        vb.drawerLayout.addDrawerListener(actionBarToggle)
        setupDrawerListener()
        actionBarToggle.syncState()
    }

    private fun setupDrawerListener() {
        vb.drawerLayout.addDrawerListener(drawerListener())
        val view = vb.navView.getHeaderView(0)
        val sideMenu = SideMenuSellMainBinding.bind(view)
        val bottomSheetFragment = BottomSheetFragment()
        sideMenu.btnMenuItemSale.setOnClickListener {
            SellMainActivity.start(this)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemClose.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetFragment")
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemReturn.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemGreeting.setOnClickListener {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
            finish()
        }
        sideMenu.btnMenuItemReport.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemHistory.setOnClickListener {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemInformation.setOnClickListener {
            replaceFragment(R.id.sell_container, AboutAppFragment.newInstance(), true)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemOperations.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun drawerListener(): DrawerLayout.DrawerListener {
        return object : DrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                vb.tbHistory.setNavigationIcon(R.drawable.ic_baseline_menu_24)
                when (getCurrentVisibleFragment()) {
                    is HistoryFragment -> setToolbarTitle(R.string.history_title)
                    is AboutAppFragment -> setToolbarTitle(R.string.info_about_app)
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setToolbarTitle(resources.getString(R.string.text_menu))
                vb.tbHistory.setNavigationIcon(R.drawable.ic_baseline_close_24)
            }
        }
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity<HistoryActivity>()
        }
    }
}