package kg.nurtelecom.changepassword.ui


import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.changepassword.core.CoreFragment
import kg.nurtelecom.user.R

import kg.nurtelecom.user.databinding.FragmentCreateNewPasswordBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class CreateNewPasswordFragment : CoreFragment<FragmentCreateNewPasswordBinding>() {

    override val vm: ChangePasswordViewModel by activityViewModels()

    fun isValidPassword(password: String?): Boolean {

        val regex = ("^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{6,16}$")

        val p: Pattern = Pattern.compile(regex)

        if (password == null) {
            return false
        }

        val m: Matcher = p.matcher(password)
        return m.matches()
    }


    private fun changePassword() {
        vb.changePasswordBtn.setOnClickListener {
            vm.changePassword(
                vm.currentPassword.value.toString(),
                vb.etNewPassword.getText(),
                vb.repeatNewPasswordEt.getText()
            )
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNewPasswordBinding {
        return FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        super.setupViews()
        changePassword()
    }
    companion object {
        fun newInstance() = CreateNewPasswordFragment()
    }
}