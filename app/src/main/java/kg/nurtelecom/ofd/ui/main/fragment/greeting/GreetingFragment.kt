package kg.nurtelecom.ofd.ui.main.fragment.greeting

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.extension.snackbar
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.FragmentGreetingBinding
import kg.nurtelecom.ofd.ui.spalsh.SplashActivity
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.user.ui.UserFragment

class GreetingFragment : CoreFragment<FragmentGreetingBinding, GreetingVM>(GreetingVM::class) {

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.main_greeting)
    }

    override fun getBinding(): FragmentGreetingBinding {
        return FragmentGreetingBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        super.setupViews()
        vb.tvInvalidate.setOnClickListener { vm.logout() }
        vb.cellFiscalMode.setOnClickListener {
            val intent = Intent(activity, SellMainActivity::class.java)
            startActivity(intent)
        }
        vb.cellProfile.setOnClickListener { Log.i("ERLAN", "cellProfile") }
        vb.cellChangePassword.setOnClickListener { Log.i("ERLAN", "cellChangePassword") }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.event.observe(viewLifecycleOwner, {
            when (it) {
                is UserLogout -> {
                    if (it.resultCode == "SUCCESS") {
                        val intent = Intent(activity, SplashActivity::class.java)
                        startActivity(intent)
                    } else {
                        vb.root.snackbar(requireContext().resources.getString(R.string.logout_fail_massage))
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(): GreetingFragment {
            return GreetingFragment()
        }
    }
}
