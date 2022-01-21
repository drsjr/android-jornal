package tour.donnees.journal.core

interface BaseMapper<A, B> {
    fun mapFrom(entity: A): B
    fun mapTo(entity: B): A
}