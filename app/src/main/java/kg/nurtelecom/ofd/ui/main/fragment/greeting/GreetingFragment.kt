package kg.nurtelecom.ofd.ui.main.fragment.greeting

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Handler
import kg.nurtelecom.auth.ui.AuthActivity
import kg.nurtelecom.changepassword.ui.ChangePasswordActivity
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.CoreEvent.Success
import kg.nurtelecom.core.extension.*
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.ofd.R
import kg.nurtelecom.ofd.databinding.FragmentGreetingBinding
import kg.nurtelecom.ofd.ui.spalsh.SplashActivity
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.user.ui.UserActivity

class GreetingFragment : CoreFragment<FragmentGreetingBinding, GreetingVM>(GreetingVM::class) {

    override fun onResume() {
        super.onResume()
        parentActivity.setToolbarTitle(R.string.main_greeting)
    }

    override fun getBinding(): FragmentGreetingBinding {
        return FragmentGreetingBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        super.setupViews()
        val startForResult = parentActivity
            .registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(activity, SplashActivity::class.java)
                startActivity(intent)
            }
        }
        vb.btnInvalidate.setOnClickListener { vm.logout() }
        vb.cellChangePassword.setOnClickListener {
          ChangePasswordActivity.start(requireContext())
        }
        vb.cellFiscalMode.setOnClickListener {
            val intent = Intent(activity, SellMainActivity::class.java)
            startForResult.launch(intent)
        }

        vb.cellProfile.setOnClickListener {
            val intent = Intent(activity, UserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.event.observe(this, {
            when (it) {
                is CoreEvent.Loading -> {
                    vb.progressbar.visible(true)
                    vb.progressbar.setProgressBarColor(kg.nurtelecom.ui.R.color.green)
                }
                is CoreEvent.Success -> {
                    val intent = Intent(activity, SplashActivity::class.java)
                    startActivity(intent)
                }
                is CoreEvent.Error -> {
                     vb.root.snackbar(requireContext().resources.getString(R.string.logout_fail_massage))
                    vb.progressbar.setProgressBarColor(kg.nurtelecom.ui.R.color.red)
                    Handler().postDelayed({
                        vb.progressbar.visible(false)
                    }, 1000)
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
