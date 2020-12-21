package kg.nurtelecom.sell.koin

import androidx.lifecycle.SavedStateHandle
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sellKoin = module {
    viewModel { SellMainViewModel() }
    single { SavedStateHandle() }
}