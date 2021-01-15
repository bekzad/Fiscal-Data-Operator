package kg.nurtelecom.user.ui

import androidx.lifecycle.Observer
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.user.R
import kg.nurtelecom.user.databinding.ActivityUserBinding
import java.util.regex.Pattern

class UserActivity : CoreActivity<ActivityUserBinding, UserVM>(UserVM::class) {

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeUserData()
    }

    private fun observeUserData() {
        vm.userData.observe(this, Observer {
            vb.etSurname.setText(it.firstname)
            vb.etName.setText(it.lastname)
            vb.etMiddlename.setText(it.middlename)
            vb.etPhone.setText(it.msisdn)
            vb.etIdentification.setText(it.inn)
        })
    }

    private fun userData() {
        val (surname, name, middleName, phone, inn) = editTextHandler()
        vm.updateUserData(surname, name, middleName, phone, inn)
    }

    private fun editTextHandler(): Array<String> {
        val surname = vb.etSurname.getText()
        val name = vb.etName.getText()
        val middleName = vb.etMiddlename.getText()
        val phone = vb.etPhone.getText()
        val inn = vb.etIdentification.getText()
        return arrayOf(surname, name, middleName, phone, inn)
    }

    override fun setupViews() {
        super.setupViews()
        vm.fetchUserData()
        setSupportActionBar(vb.toolbar)
        setToolbarTitle("Профиль")
        vb.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        vb.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        vb.etSurname.setTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty())
        }

        vb.etName.setTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty())
        }
        vb.etPhone.setTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty())
        }

        vb.btnChange.setOnClickListener { userData()

        }

        vb.etIdentification.setTextChangedListener {
            validateOfTheIdentificationNumber()
        }
    }

    override fun getBinding() = ActivityUserBinding.inflate(layoutInflater)

    private fun validateOfTheIdentificationNumber(): Boolean {

        val ID_PATTERN =
            Pattern.compile("^(1|2)(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])(19|20)\\d\\d\\d\\d\\d\\d\\d\$")
        val identificationNumberInput = vb.etIdentification.getText().trim()
        if (!ID_PATTERN.matcher(identificationNumberInput).matches()) {
            vb.etIdentification.error = "The ID number is not correct"
            return false
        } else {
            vb.etIdentification.error = null
            return true
        }
    }
}