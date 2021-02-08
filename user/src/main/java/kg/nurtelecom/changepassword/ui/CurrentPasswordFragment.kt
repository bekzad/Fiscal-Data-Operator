package kg.nurtelecom.changepassword.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.changepassword.core.CoreFragment
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.user.R
import kg.nurtelecom.user.databinding.FragmentCurrentPasswordBinding


class CurrentPasswordFragment : CoreFragment<FragmentCurrentPasswordBinding>() {

    override val vm: ChangePasswordViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCurrentPasswordBinding {
        return FragmentCurrentPasswordBinding.inflate(inflater,container,false)
    }

    override fun setupViews() {
        vb.nextBtn.setOnClickListener {
            vm.saveCurrentPassword(vb.currentPasswordEt.getText())
            navigateToCreateNewPasswordFragment()
        }
    }

    private fun navigateToCreateNewPasswordFragment() {
        val activity = activity as AppCompatActivity
        activity.replaceFragment<CreateNewPasswordFragment>(R.id.container_main)
    }

    companion object {
        fun newInstance() = CurrentPasswordFragment()
    }
}