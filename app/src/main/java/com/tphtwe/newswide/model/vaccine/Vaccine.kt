package com.tphtwe.newswide.model.vaccine

data class Vaccine(
    val `data`: List<Data>,
    val phases: List<Phase>,
    val source: String,
    val totalCandidates: String
)