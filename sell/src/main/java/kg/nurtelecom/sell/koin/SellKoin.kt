package kg.nurtelecom.sell.koin

import kg.nurtelecom.sell.repository.HistoryRepository
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModelImpl
import kg.nurtelecom.sell.ui.activity.SellMainViewModelImpl
import kg.nurtelecom.sell.ui.fragment.credit.CreditListVM
import kg.nurtelecom.sell.ui.fragment.credit.CreditListVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sellKoin = module {
    viewModel<HistoryViewModel> { HistoryViewModelImpl(get()) }
    single { HistoryRepository(get(), get()) }
    viewModel<SellMainViewModel> { SellMainViewModelImpl() }
    viewModel <CreditListVM>{ CreditListVMImpl(get()) }
}
