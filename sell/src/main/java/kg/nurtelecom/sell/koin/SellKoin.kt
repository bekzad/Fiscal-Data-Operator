package kg.nurtelecom.sell.koin

import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.story.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.story.HistoryViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sellKoin = module {
    viewModel { SellMainViewModel() }
    viewModel<HistoryViewModel> { HistoryViewModelImpl(get()) }
    single { SellRepository(get(), get(), get()) }
}
