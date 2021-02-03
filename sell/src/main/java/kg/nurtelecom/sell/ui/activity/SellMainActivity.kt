package kg.nurtelecom.sell.ui.activity

import android.content.Context
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.startActivity
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.bottom_sheet.BottomSheetFragment
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.setupActionBarDrawerToggle

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class) {

    private lateinit var drawerLayout: DrawerLayout

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        setSupportActionBar(vb.tbSellMain)
        setupDrawerLayout()
        setupRegime()
        vb.tbSellMain.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        replaceFragment<SellFragment>(R.id.sell_container, false)
    }

    private fun setupDrawerLayout() {
        drawerLayout = vb.drawerLayout
        drawerLayout.setupActionBarDrawerToggle(this)
        setupNavigationListener()
    }

    private fun setupRegime() {
        vb.mcRegime.visible(vm.isRegimeNonFiscal)
    }

    private fun setupNavigationListener() {
        val headerView = vb.navView.inflateHeaderView(R.layout.side_menu_sell_main)
        val sideMenu = SideMenuSellMainBinding.bind(headerView)
        val bottomSheetFragment = BottomSheetFragment()

        sideMenu.btnDrawerClose.setOnClickListener {
            closeNavDrawer()
        }

        sideMenu.btnMenuItemSale.setOnClickListener {
            closeNavDrawer()
            replaceFragment<SellFragment>(R.id.sell_container)
        }
        sideMenu.btnMenuItemClose.setOnClickListener {
            closeNavDrawer()
            bottomSheetFragment.show(supportFragmentManager, "BottomSheetFragment")
        }
        sideMenu.btnMenuItemReturn.setOnClickListener {
            closeNavDrawer()
        }
        sideMenu.btnMenuItemGreeting.setOnClickListener {
            closeNavDrawer()
            finish()
        }
        sideMenu.btnMenuItemReport.setOnClickListener {
            closeNavDrawer()
        }
        sideMenu.btnMenuItemHistory.setOnClickListener {
            closeNavDrawer()
            HistoryActivity.start(this)
            finishAndRemoveTask()
        }
        sideMenu.btnMenuItemInformation.setOnClickListener {
            closeNavDrawer()
            replaceFragment<AboutAppFragment>(R.id.sell_container)
        }
        sideMenu.btnMenuItemOperations.setOnClickListener {
            closeNavDrawer()
        }
    }

    private fun closeNavDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity<SellMainActivity>()
        }
    }
}