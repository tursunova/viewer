import java.util.*

interface Subject {
    var observers: ArrayList<Observer>

    fun attachObserver (observer: Observer) {
        if (!observers.contains(observer))
            observers.add(observer)
    }

    fun detachObserver (observer: Observer) {
        observers.remove(observer)
    }

    fun Notify()
}