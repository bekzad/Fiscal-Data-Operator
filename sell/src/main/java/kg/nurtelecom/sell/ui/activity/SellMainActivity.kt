package kg.nurtelecom.sell.ui.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.*
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.bottom_sheet.BottomSheetFragment
import kg.nurtelecom.sell.ui.fragment.history.HistoryFragment
import kg.nurtelecom.sell.ui.fragment.other_operations.OtherOperationsFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.ui.fragment.refund.RefundFragment
import kg.nurtelecom.sell.ui.fragment.report.XReportFragment
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.setupActionBarDrawerToggle

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class){

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        toolbar = vb.tbSellMain
        setSupportActionBar(toolbar)
        setupDrawerLayout()
        setupRegime()
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        replaceFragment<SellFragment>(R.id.sell_container, false)
    }

    override fun subscribeToLiveData() {
        vm.event.observe(this, {
            when (it) {
                is UserLogout -> {
                    if (it.resultCode == "SUCCESS") {
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        vb.root.snackbar(application.resources.getString(R.string.logout_fail_massage))
                    }
                }
            }
        })
    }

    private fun setupDrawerLayout() {
        drawerLayout = vb.drawerLayout
        drawerLayout.setupActionBarDrawerToggle(this)
        setupNavigationListener()
        setSupportActionBar(vb.tbSellMain)
        setupOperationType()
        if (vm.operationType == OperationType.PREPAY)
            replaceFragment<PaymentMethodFragment>(R.id.sell_container)
        else
            replaceFragment<SellFragment>(R.id.sell_container)
    }

    private fun setupOperationType() {
        vm.operationType = when(intent?.getStringExtra("operationType")) {
            OperationType.POSTPAY.type -> OperationType.POSTPAY
            OperationType.PREPAY.type -> OperationType.PREPAY
            else -> OperationType.SALE
        }
    }

    private fun setupRegime() {
        vb.mcRegime.visible(vm.isRegimeNonFiscal)
    }

    private fun setupNavigationListener() {
        val headerView = vb.navView.inflateHeaderView(R.layout.side_menu_sell_main)
        val sideMenu = SideMenuSellMainBinding.bind(headerView)
        sideMenu.tvSessionDateOpening.text = vm.fetchCurrentDate()
        sideMenu.btnDrawerClose.setOnClickListener {
            closeNavDrawer()
        }
        sideMenu.btnMenuItemSale.setOnClickListener {
            closeNavDrawer()
            replaceFragment<SellFragment>(R.id.sell_container)
            vm.operationType = OperationType.SALE
        }
        sideMenu.btnMenuItemClose.setOnClickListener {
            closeNavDrawer()
            BottomSheetFragment.newInstance(supportFragmentManager)
        }
        sideMenu.btnMenuItemReturn.setOnClickListener {
            closeNavDrawer()
            replaceFragment<RefundFragment>(R.id.sell_container)
        }
        sideMenu.btnMenuItemGreeting.setOnClickListener {
            closeNavDrawer()
            finish()
        }
        sideMenu.btnMenuItemReport.setOnClickListener {
            replaceFragment<XReportFragment>(R.id.sell_container)
            closeNavDrawer()
        }
        sideMenu.btnMenuItemHistory.setOnClickListener {
            replaceFragment<HistoryFragment>(R.id.sell_container)
            closeNavDrawer()
        }
        sideMenu.btnMenuItemInformation.setOnClickListener {
            replaceFragment<AboutAppFragment>(R.id.sell_container)
            closeNavDrawer()
        }
        sideMenu.btnMenuItemOperations.setOnClickListener {
            replaceFragment<OtherOperationsFragment>(R.id.sell_container)
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

        fun start(context: Context?, operationType: OperationType) {
            context?.startActivity<SellMainActivity> {
                this.putExtra("operationType", operationType.type)
            }
        }
    }
}