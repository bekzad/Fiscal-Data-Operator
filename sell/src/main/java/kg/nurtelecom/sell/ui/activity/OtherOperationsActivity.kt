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
import kg.nurtelecom.sell.databinding.ActivityOtherOperationsBinding
import kg.nurtelecom.sell.databinding.SideMenuOtherOperationsBinding
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment

class OtherOperationsActivity :
    CoreActivity<ActivityOtherOperationsBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun getBinding(): ActivityOtherOperationsBinding =
        ActivityOtherOperationsBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbOtherOperations)
        setupNavDrawer()
        replaceFragment(R.id.operations_container, SellFragment.newInstance())
    }

    private fun setupNavDrawer() {
        val actionBarToggle = ActionBarDrawerToggle(
            this,
            vb.drawerLayout,
            vb.tbOtherOperations,
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
        val sideMenu = SideMenuOtherOperationsBinding.bind(view)
        vb.drawerLayout.openDrawer(GravityCompat.START)
        sideMenu.btnMenuItemPurchase.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemReturnPurchase.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemRefundPrepayment.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemCredit.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemCloseCredit.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemRepaymentCredit.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemDepositAndPayment.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemHistoryDepositsPayments.setOnClickListener {
            // here place for replacing fragment
            vb.drawerLayout.closeDrawer(GravityCompat.START)
        }
        sideMenu.btnMenuItemFiscalReport.setOnClickListener {
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
                when (getCurrentVisibleFragment()) {
                    is AboutAppFragment -> setToolbarTitle(R.string.info_about_app)
                    else -> finish()
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setToolbarTitle(resources.getString(R.string.text_operations))
                vb.tbOtherOperations.setNavigationIcon(R.drawable.ic_baseline_close_24)
            }
        }
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity<OtherOperationsActivity>()
        }
    }
}