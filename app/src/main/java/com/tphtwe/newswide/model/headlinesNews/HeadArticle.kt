package com.tphtwe.newswide.model.headlinesNews

data class HeadArticle(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: HeadSource,
    val title: String,
    val url: String,
    val urlToImage: String
)