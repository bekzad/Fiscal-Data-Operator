package kg.nurtelecom.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.CoreEvent.Success
import kg.nurtelecom.core.CoreEvent.Error
import kg.nurtelecom.core.CoreEvent.Loading
import kg.nurtelecom.core.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class CoreViewModel : ViewModel() {

    var event: MutableLiveData<Event> = MutableLiveData()

    fun safeCall(dispatcher: CoroutineDispatcher = Dispatchers.Main, func: suspend () -> Unit) {
        viewModelScope.launch(dispatcher) {
            try {
                event.postValue(Loading(true))
                event.postValue(Success(func()))
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        var message = ""
                        when(throwable.code()) {
                            401 -> message = "Неверные учетные данные"
                        }
                        event.postValue(Error(false, throwable.code(), throwable.response()?.errorBody(), message))
                    }
                    else -> {
                        event.postValue(Error(true, null, null, "Ошибка сети. Попробуйте заново."))
                    }
                }
            }
        }
    }

    fun error(): String {
        return "Error"
    }
}