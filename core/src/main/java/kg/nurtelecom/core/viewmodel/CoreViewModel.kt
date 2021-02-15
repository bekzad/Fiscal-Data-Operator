package kg.nurtelecom.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.ErrorHandler
import kg.nurtelecom.core.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CoreViewModel : ViewModel() {

    val event: MutableLiveData<Event> = MutableLiveData()

    fun safeCall(dispatcher: CoroutineDispatcher = Dispatchers.Main, func: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) {
            try {
                func()
            } catch (e: Exception) {
//                throw ErrorHandler(e.message)
                e.printStackTrace()
            }
        }
    }
}