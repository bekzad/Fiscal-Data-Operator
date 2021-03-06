package kg.nurtelecom.ofd.koin

import kg.nurtelecom.auth.ui.AuthViewModel
import kg.nurtelecom.auth.ui.AuthViewModelImpl
import kg.nurtelecom.ofd.repository.GreetingRepository
import kg.nurtelecom.ofd.repository.SplashRepository
import kg.nurtelecom.ofd.ui.main.fragment.greeting.GreetingVM
import kg.nurtelecom.ofd.ui.main.fragment.greeting.GreetingVMImpl
import kg.nurtelecom.ofd.ui.spalsh.SplashVM
import kg.nurtelecom.ofd.ui.spalsh.SplashVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoin = module {

    viewModel<GreetingVM> { GreetingVMImpl(get()) }
    viewModel<AuthViewModel> { AuthViewModelImpl(get()) }
    viewModel<SplashVM> { SplashVMImpl(get()) }

    single { GreetingRepository(get(), get()) }
    single { SplashRepository(get()) }
}
