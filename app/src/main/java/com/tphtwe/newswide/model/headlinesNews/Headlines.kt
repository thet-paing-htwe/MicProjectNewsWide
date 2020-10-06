package com.tphtwe.newswide.model.headlinesNews

data class Headlines(
    val articles: List<HeadArticle>,
    val status: String,
    val totalResults: Int
)