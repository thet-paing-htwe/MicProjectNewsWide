package com.tphtwe.newswide.model.vaccine

data class Data(
    val details: String,
    val developerResearcher: List<String>,
    val lastUpdate: String,
    val medicationClass: String,
    val sponsors: List<String>,
    val tradeName: List<String>,
    val trialPhase: String
)