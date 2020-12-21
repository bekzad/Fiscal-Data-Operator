package kg.nurtelecom.core

open class Event
open class CoreEvent : Event() {
    class Notification(val message: String) : CoreEvent()
    class Error(val errorMessage: String) : CoreEvent()
}