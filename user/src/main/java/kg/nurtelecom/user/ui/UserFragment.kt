package kg.nurtelecom.user.ui

import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.user.databinding.FragmentUserBinding

class UserFragment : CoreFragment<FragmentUserBinding, UserVM>(UserVM::class) {

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()

    }

    override fun getBinding() = FragmentUserBinding.inflate(layoutInflater)

    companion object{
        fun getInctance() = UserFragment()
    }
}

