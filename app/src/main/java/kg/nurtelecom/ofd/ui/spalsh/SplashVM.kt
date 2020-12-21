package kg.nurtelecom.ofd.ui.spalsh

import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.ofd.repository.SplashRepository

abstract class SplashVM : CoreViewModel(){
    abstract fun isSign(): Boolean
}

class SplashVMImpl(private val repository: SplashRepository) : SplashVM() {
    override fun isSign(): Boolean {
        return repository.isSign()
    }

}