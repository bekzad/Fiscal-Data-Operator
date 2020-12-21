package kg.nurtelecom.user.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.user.databinding.FragmentUserBinding

class UserFragment : CoreFragment<FragmentUserBinding, UserVM>(UserVM::class) {

    private lateinit var binding: FragmentUserBinding
    private lateinit var uservm: UserVM

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeUserData()
    }

    private fun observeUserData(){
        vm.userData.observe(this, Observer {
            vb.etSurname.setText(it.middleName)
            vb.etName.setText(it.firstName)
            vb.etLastname.setText(it.lastName)
            vb.etPhone.setText(it.msiSdn)
            vb.etIdentification.setText(it.inn)
        })

    }

    override fun getBinding() = FragmentUserBinding.inflate(layoutInflater)

}

