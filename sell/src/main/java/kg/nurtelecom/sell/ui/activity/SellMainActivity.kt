package kg.nurtelecom.sell.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ActivitySellMainBinding
import kg.nurtelecom.sell.databinding.SideMenuSellMainBinding
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment

class SellMainActivity :
    CoreActivity<ActivitySellMainBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.tbSellMain)
        setupNavDrawer()
        replaceFragment(R.id.container, SellFragment.newInstance())
    }

    private fun setupNavDrawer() {
        val actionBarToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this,vb.drawerLayout, vb.tbSellMain,R.string.content_text, R.string.content_text )
        actionBarToggle.drawerArrowDrawable.color = resources.getColor(R.color.colorWhite)
        vb.drawerLayout.addDrawerListener(actionBarToggle)
        setupDrawerListener()
        actionBarToggle.syncState()
    }

    private fun setupDrawerListener() {
        vb.drawerLayout.addDrawerListener(drawerListener())
        val view = vb.navView.getHeaderView(0)
        val v = SideMenuSellMainBinding.bind(view)
    }

    private fun drawerListener(): DrawerLayout.DrawerListener {
        return object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerOpened(drawerView: View) {
                setToolbarTitle(resources.getString(R.string.text_menu))
            }
        }
    }

    override fun getBinding(): ActivitySellMainBinding =
        ActivitySellMainBinding.inflate(layoutInflater)
}
