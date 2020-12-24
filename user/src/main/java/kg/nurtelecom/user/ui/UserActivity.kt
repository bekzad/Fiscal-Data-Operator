package kg.nurtelecom.user.ui

import androidx.lifecycle.Observer
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.user.databinding.ActivityUserBinding

class UserActivity : CoreActivity<ActivityUserBinding, UserVM>(UserVM::class) {

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeUserData()
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

    companion object {

        fun newInstance(): UserActivity{
            return UserActivity()
        }

    }

}

