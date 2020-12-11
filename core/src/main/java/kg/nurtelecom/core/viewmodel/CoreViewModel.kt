package kg.nurtelecom.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.Event
import kotlinx.coroutines.launch

class CoreViewModel : ViewModel() {

    val event: MutableLiveData<Event> = MutableLiveData()

    fun safeCall(func: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                func()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}