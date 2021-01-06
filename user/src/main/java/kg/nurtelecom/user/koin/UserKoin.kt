package kg.nurtelecom.user.koin

import kg.nurtelecom.storage.roomDatabase.RoomDB
import kg.nurtelecom.user.repository.UserRepository
import kg.nurtelecom.user.ui.UserVM
import kg.nurtelecom.user.ui.UserVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userKoin = module {

    viewModel<UserVM> { UserVMImpl() }
    single { UserRepository(get()) }
}