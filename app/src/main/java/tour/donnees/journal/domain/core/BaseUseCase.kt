package tour.donnees.journal.domain.core

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseUseCase {
    fun <T> invoke(o: Observable<T>): Observable<T> {
        return o.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
    }
}