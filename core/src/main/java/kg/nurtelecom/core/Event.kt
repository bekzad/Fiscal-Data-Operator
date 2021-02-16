package kg.nurtelecom.core

import okhttp3.ResponseBody

open class Event
sealed class CoreEvent : Event() {
    class Success(val data: Unit) : CoreEvent()
    class Notification(val message: String) : CoreEvent()
    class Error(val isNetworkError: Boolean,
                val errorCode: Int?,
                val errorBody: ResponseBody?,
                val message: String) : CoreEvent()
    class Loading(val isLoading: Boolean): CoreEvent()
}