package kg.nurtelecom.sell.ui.activity

import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.*
import kg.nurtelecom.ofd.fragments.aboutapp.AboutAppFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.bottom_sheet.BottomSheetFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentByCardFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentByCashFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.addMFragment
import kg.nurtelecom.sell.utils.drawerClosed
import kg.nurtelecom.sell.utils.drawerOpened
import kg.nurtelecom.sell.utils.setupActionBarDrawerToggle

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class) {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        drawerLayout = vb.drawerLayout
        toolbar = vb.tbSellMain
        setupRegime()
        setSupportActionBar(toolbar)
        setupNavDrawer()
        addMFragment<SellFragment>(R.id.sell_container, false)
    }

    private fun setupRegime() {
        vb.mcRegime.visible(vm.isRegimeNonFiscal)
    }

    private fun setupNavDrawer() {
        drawerLayout.setupActionBarDrawerToggle(this, toolbar, ::setupNavigationListener)

        drawerLayout.drawerClosed {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
            handleToolbarChanges()
        }
        drawerLayout.drawerOpened {
            setToolbarTitle(resources.getString(R.string.text_menu))
            toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        }
    }

    private fun setupNavigationListener() {
        val view = vb.navView.getHeaderView(0)
        val sideMenu = SideMenuSellMainBinding.bind(view)
        val bottomSheetFragment = BottomSheetFragment()

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
            drawerLayout.closeDrawer(GravityCompat.START)
            replaceFragment<AboutAppFragment>(R.id.sell_container)
        }
        sideMenu.btnMenuItemOperations.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun handleToolbarChanges() {
        when (getCurrentVisibleFragment()) {
            is AboutAppFragment -> setToolbarTitle(R.string.info_about_app)
            is SellFragment -> setToolbarTitle(R.string.text_sale)
            is AddProductFragment -> setToolbarTitle(R.string.product_selection)
            is PriceOutputFragment -> setToolbarTitle(R.string.price_entry)
            is PaymentMethodFragment, is PaymentByCardFragment,
            is PaymentByCashFragment -> setToolbarTitle(R.string.payment_method)
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