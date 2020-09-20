package com.tphtwe.newswide.model.allNews

data class All(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)