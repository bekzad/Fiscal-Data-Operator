package kg.nurtelecom.user.ui

import androidx.lifecycle.Observer
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.user.databinding.ActivityUserBinding
import java.util.regex.Pattern

class UserActivity : CoreActivity<ActivityUserBinding, UserVM>(UserVM::class) {

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeUserData()
        validateOfTheIdentificationNumber ()
    }
    private fun observeUserData(){
        vm.userData.observe(this, Observer {
            vb.etSurname.setText(it.middlename)
            vb.etName.setText(it.firstname)
            vb.etLastname.setText(it.lastname)
            vb.etPhone.setText(it.msisdn)
            vb.etIdentification.setText(it.inn)
        })
    }
    override fun setupViews() {
        super.setupViews()
        vm.fetchUserData()
    }
    override fun getBinding() = ActivityUserBinding.inflate(layoutInflater)

    private fun validateOfTheIdentificationNumber (): Boolean {
        val ID_PATTERN =
            Pattern.compile("^(1|2)(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])(19|20)\\d\\d\\d\\d\\d\\d\\d\$")
        val identificationNumberInput = vb.etIdentification.text.toString().trim()
        if (!ID_PATTERN.matcher(identificationNumberInput).matches()) {
            vb.etIdentification.error = "The ID number is not correct"
            return false
        }else{
            vb.etIdentification.error =  null
            return true
        }
    }

    companion object {

        fun newInstance(): UserActivity{
            return UserActivity()
        }
    }
}

