package kg.nurtelecom.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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