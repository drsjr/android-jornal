package tour.donnees.journal.utils

import java.text.SimpleDateFormat

class DateUtils {

    fun compareDate(a: String, b: String): Boolean {

        val formatter = SimpleDateFormat(FORMAT)

        val t = formatter.parse(a)
        val i = formatter.parse(b)
        return t?.let {
            return@let it > i
        } ?: run {
            return@run true
        }
    }

    companion object {
        const val FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
    }
}