package com.tphtwe.newswide.ui.all

import org.ocpsoft.prettytime.PrettyTime
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

fun dateFormat(publishedAt: String): String {
    val inputFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        Locale.ENGLISH
    )
    val outputFormat = SimpleDateFormat(
        "E , dd MMM yy",
        Locale.getDefault()
    )

    val date = inputFormat.parse(publishedAt)
    return outputFormat.format(date)

}

fun DateToTimeFormat(oldstringDate: String?): String? {
    val p = PrettyTime()
    var isTime: String? = null
    val sdf = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        Locale.ENGLISH
    )
    val time = sdf.parse(oldstringDate)
    isTime = p.format(time)

    return isTime
}
fun getFormatedNumber(count: Long): String {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
}
fun percentage(current:Double,total:Double):String{
    var percent:Double=(current/total)*100.0
    var df=DecimalFormat("#.#")
    df.roundingMode=RoundingMode.CEILING
    return (df.format(percent))
}