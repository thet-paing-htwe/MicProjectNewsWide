package com.tphtwe.newswide.ui.all

import android.animation.ObjectAnimator
import android.widget.TextView
import org.ocpsoft.prettytime.PrettyTime
import java.math.RoundingMode
import java.text.DecimalFormat
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

fun dateFormat2(selectedDate: String): String {
    val inputFormat = SimpleDateFormat(
        "yyyy-MM-d",
        Locale.ENGLISH
    )
    val outputFormat = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )

    val date2 = inputFormat.parse(selectedDate)
    return outputFormat.format(date2)

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
    return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "KMBTPE"[exp - 1])
}
fun percentage(current:Double,total:Double):String{
    var percent:Double=(current/total)*100.0
    var df=DecimalFormat("#.#")
    df.roundingMode=RoundingMode.CEILING
    return (df.format(percent))
}
fun clearText(text:String):String{
    var cleanText=text
    if ((cleanText).contains("&rsquo;s") || (cleanText).contains("&ldquo;") || (cleanText).contains(
            "&rdquo;") || (cleanText).contains("&nbsp;") || (cleanText).contains("&micro;") || (cleanText).contains(
            "&quot;")||(cleanText.contains("[]")|| (cleanText).contains("&nbsp") || (cleanText).contains(",&nbsp")
                || (cleanText).contains("&rsquo")|| (cleanText).contains("Outcomes:") || (cleanText).contains("Funding:")
                || (cleanText).contains("Status:") || (cleanText).contains("Study Design:") || (cleanText).contains("Study Designs:")
                || (cleanText).contains("&mu;")||(cleanText).contains("Regulatory Actions:")||(cleanText).contains("Trials:"))
    ) {
        cleanText = cleanText.replace("&rsquo;s", "")
        cleanText = cleanText.replace("&ldquo;", "")
        cleanText = cleanText.replace("&rdquo;", "")
        cleanText = cleanText.replace("&nbsp;", "")
        cleanText = cleanText.replace("&micro;", "\u00B5")
        cleanText = cleanText.replace("&mu;", "\u00B5")
        cleanText = cleanText.replace("&quot;", "")
        cleanText = cleanText.replace(",&nbsp", "")
        cleanText = cleanText.replace("&nbsp", "")
        cleanText = cleanText.replace("&rsquo", "")
        cleanText = cleanText.replace("Outcomes:", "\n\nOutcomes:")
        cleanText = cleanText.replace("Funding:", "\n\nFunding:")
        cleanText = cleanText.replace("Status:", "\n\nStatus:")
        cleanText = cleanText.replace("Study Design:", "\n\nStudy Design:")
        cleanText = cleanText.replace("Study Designs:", "\n\nStudy Designs:")
        cleanText = cleanText.replace("Regulatory actions:", "\n\nRegulatory actions:")
        cleanText = cleanText.replace("Trials:", "\n\nTrials:")
        cleanText = cleanText.replace("[]", "No information Provided")
    }
    return cleanText

}
//fun crossFadeIn(tv:TextView,fadeIn:Animation){
//    tv.startAnimation(fadeIn)
//    tv.maxLines=3
//}
//fun crossFadeOut(tv:TextView,fadeOut:Animation){
//    tv.maxLines=Integer.MAX_VALUE
//    tv.startAnimation(fadeOut)
//
//}

fun textanimation(tv: TextView) {
    val collapsedMaxLines = 3
    val animation = ObjectAnimator.ofInt(
        tv, "maxLines",
        if (tv.maxLines == collapsedMaxLines) tv.lineCount
        else collapsedMaxLines
    )
    animation.setDuration(500).start()
}

fun getCountry(): String {
    val locale = Locale.getDefault()
    val country = locale.country.toString()
    return country.toLowerCase()
}

fun getLanguage(): String {
    val locale = Locale.getDefault()
    val country = locale.language.toString()
    return country.toLowerCase()
}
