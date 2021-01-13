package kg.nurtelecom.sell.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.getCurrentVisibleFragment
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.menu.DrawerListener
import kg.nurtelecom.ofd.aboutapp.AboutAppFragment
import kg.nurtelecom.core.extension.startActivity
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.bottom_sheet.BottomSheetFragment
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.ui.fragment.history.HistoryFragment
import kg.nurtelecom.sell.utils.replaceFragment

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbSellMain)
        setupNavDrawer()
        replaceFragment(R.id.sell_container, SellFragment.newInstance())
    }

    private fun setupNavDrawer() {
        val actionBarToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            vb.drawerLayout,
            vb.tbSellMain,
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
        val v = SideMenuSellMainBinding.bind(view)
        val bottomSheetFragment = BottomSheetFragment()
        v.sellMainMenuItemSale.setOnClickListener {
            replaceFragment(R.id.sell_container, SellFragment.newInstance(), true)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemClose.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetFragment")
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemReturn.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemGreeting.setOnClickListener {
            vb.drawerLayout.closeDrawer(GravityCompat.START)
            finish()
        }
        v.sellMainMenuItemReport.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemHistory.setOnClickListener {
            replaceFragment(R.id.sell_container, HistoryFragment.newInstance(), true)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemInformation.setOnClickListener {
            replaceFragment(R.id.sell_container, AboutAppFragment.newInstance(), true)
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        v.sellMainMenuItemOperations.setOnClickListener {
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
                vb.tbSellMain.setNavigationIcon(R.drawable.ic_baseline_menu_24)
                when (getCurrentVisibleFragment()) {
                    is HistoryFragment -> setToolbarTitle(R.string.history_title)
                    is AboutAppFragment -> setToolbarTitle(R.string.info_about_app)
                    is SellFragment -> setToolbarTitle(R.string.text_sale)
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setToolbarTitle(resources.getString(R.string.text_menu))
                vb.tbSellMain.setNavigationIcon(R.drawable.ic_baseline_close_24)
            }
        }
    }

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)

    companion object {
        fun start(context: Context?) {
            context?.startActivity<SellMainActivity>()
        }
    }
}