package kg.nurtelecom.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CoreViewModel() : ViewModel() {

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

    fun safeUICall(func: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                func()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}