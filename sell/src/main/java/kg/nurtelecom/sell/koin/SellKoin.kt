package kg.nurtelecom.sell.koin

import kg.nurtelecom.sell.repository.HistoryRepository
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.repository.SessionRepository
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.activity.SellMainViewModelImpl
import kg.nurtelecom.sell.ui.fragment.credit.CreditCheckViewFragmentVM
import kg.nurtelecom.sell.ui.fragment.credit.CreditCheckViewFragmentVMImpl
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModelImpl
import kg.nurtelecom.sell.ui.fragment.credit.CreditListVM
import kg.nurtelecom.sell.ui.fragment.credit.CreditListVMImpl
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history.ReceiptInOutHistoryVM
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history.ReceiptInOutHistoryVMImpl
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out.ReceiptInOutVM
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out.ReceiptInOutVMImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sellKoin = module {
    single { HistoryRepository(get(), get()) }
    single { SellRepository(get(), get(), get(), get(), get()) }
    single { SessionRepository(get(), get()) }
    single { ReceiptInOutRepository(get(), get()) }
    viewModel<SellMainViewModel> { SellMainViewModelImpl(get(), get()) }
    viewModel<HistoryViewModel> { HistoryViewModelImpl(get()) }
    viewModel <CreditListVM> { CreditListVMImpl(get()) }
    viewModel <CreditCheckViewFragmentVM> { CreditCheckViewFragmentVMImpl(get()) }
    viewModel<ReceiptInOutVM> { ReceiptInOutVMImpl(get()) }
    viewModel<ReceiptInOutHistoryVM> { ReceiptInOutHistoryVMImpl(get()) }
}
