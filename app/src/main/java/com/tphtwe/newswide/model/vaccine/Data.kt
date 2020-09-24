package com.tphtwe.newswide.model.vaccine

data class Data(
    val candidate: String,
    val details: String,
    val mechanism: String,
    val institutions: List<String>,
    val sponsors: List<String>,
    val trialPhase: String,
    var expand:Boolean
)