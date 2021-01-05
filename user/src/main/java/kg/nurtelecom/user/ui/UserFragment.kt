package kg.nurtelecom.user.ui

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.ui.databinding.InputTextFieldExampleBinding
import kg.nurtelecom.user.R
import kg.nurtelecom.user.databinding.FragmentUserBinding

class UserFragment : CoreFragment<InputTextFieldExampleBinding, UserVM>(UserVM::class) {

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
//        observeUserData()
    }

//    private fun observeUserData(){
//        vm.userData.observe(this, Observer {
//            vb.etSurname.setText(it.middleName)
//            vb.etName.setText(it.firstName)
//            vb.etLastname.setText(it.lastName)
//            vb.etPhone.setText(it.msiSdn)
//            vb.etIdentification.setText(it.inn)
//        })
//    }

    override fun getBinding() = InputTextFieldExampleBinding.inflate(layoutInflater)

//    override fun setupViews() {
//        super.setupViews()
//        vb.testPassword.apply {
//
//            setHint("your text")
//            setPasswordEditText(true)
////            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
////            setOnIconChanged {
////                this.switchIconType()
////            }
//        }
//    }

    companion object{
        fun getInctance() = UserFragment()
    }

}

