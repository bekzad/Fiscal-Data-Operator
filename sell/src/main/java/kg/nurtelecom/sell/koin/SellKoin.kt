package kg.nurtelecom.sell.koin

import kg.nurtelecom.sell.repository.HistoryRepository
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.repository.SessionRepository
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.activity.SellMainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sellKoin = module {
    single { HistoryRepository(get(), get()) }
    single { SellRepository(get(), get()) }
    single { SessionRepository(get(), get()) }
    viewModel<SellMainViewModel> { SellMainViewModelImpl(get(), get(), get()) }
}
