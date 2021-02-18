package kg.nurtelecom.ofd.ui.main.fragment.greeting

import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.ofd.repository.GreetingRepository

abstract class GreetingVM : CoreViewModel() {
    abstract fun logout()
}

class GreetingVMImpl(private val greetingRepository: GreetingRepository): GreetingVM() {

    override fun logout() {
        safeCall {
            greetingRepository.logout()
        }
    }

}