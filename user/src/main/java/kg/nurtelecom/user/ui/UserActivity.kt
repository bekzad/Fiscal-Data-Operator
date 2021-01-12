package kg.nurtelecom.user.ui

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.text
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
            vb.etSurname.setText(it.firstname)
            vb.etName.setText(it.lastname)
            vb.etMiddlename.setText(it.middlename)
            vb.etPhone.setText(it.msisdn)
            vb.etIdentification.setText(it.inn)
        })
    }

    private fun userData(){
        val (surname, name, middleName, phone, inn) = editTextHandler()
        vm.updateUserData(surname, name, middleName, phone, inn)

    }

    private fun editTextHandler(): Array<String> {
        val surname = text(vb.etSurname)
        val name = text(vb.etName)
        val middleName = text(vb.etMiddlename)
        val phone = text(vb.etPhone)
        val inn = text(vb.etIdentification)
        return arrayOf(surname, name, middleName, phone, inn)
    }

    override fun setupViews() {
        super.setupViews()
        vm.fetchUserData()

        vb.etSurname.addTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty())
        }

        vb.etName.addTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty())
        }
        vb.etPhone.addTextChangedListener {
            val (surname, name, phone) = editTextHandler()
            vb.btnChange.enable(surname.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) // ? wtf
        }

        vb.btnChange.setOnClickListener { userData() }
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

//fun isValidPassword(password: String?): Boolean {
//
//        val regex = ("^(?=.*[0-9])"
//                + "(?=.*[a-z])(?=.*[A-Z])"
//                + "(?=\\S+$).{6,16}$")
//
//        val p: Pattern = Pattern.compile(regex)
//
//        if (password == null) {
//            return false
//        }
//
//        val m: Matcher = p.matcher(password)
//        return m.matches()
//    }

