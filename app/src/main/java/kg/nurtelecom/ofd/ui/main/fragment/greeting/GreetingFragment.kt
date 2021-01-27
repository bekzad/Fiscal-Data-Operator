package kg.nurtelecom.ofd.ui.main.fragment.greeting

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.changepassword.ui.ChangePasswordActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.extension.snackbar
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.FragmentGreetingBinding
import kg.nurtelecom.ofd.ui.spalsh.SplashActivity
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.user.ui.UserActivity

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
        vb.btnInvalidate.setOnClickListener { vm.logout() }
        vb.cellChangePassword.setOnClickListener {
          ChangePasswordActivity.start(requireContext())
        }
        vb.cellFiscalMode.setOnClickListener {
            SellMainActivity.start(requireContext())
        }

        vb.cellProfile.setOnClickListener {
            val intent = Intent(activity, UserActivity::class.java)
            startActivity(intent)
        }
        vb.btnTempCredit.setOnClickListener {
            SellMainActivity.start(requireContext(), OperationType.POSTPAY)
        }
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
