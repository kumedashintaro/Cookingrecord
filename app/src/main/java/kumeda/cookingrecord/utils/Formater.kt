package kumeda.cookingrecord.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    val sdFormat = try {
        SimpleDateFormat(pattern)
    } catch (e: IllegalArgumentException) {
        null
    }
    val date = sdFormat?.let {
        try {
            it.parse(this)
        } catch (e: ParseException) {
            null
        }
    }
    return date
}


class FormateryyyyMMdd {
    companion object {
        val df = SimpleDateFormat("yyyy-MM-dd")
    }
}

class FormateryyyyMMddHHmmss {
    companion object {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
}
