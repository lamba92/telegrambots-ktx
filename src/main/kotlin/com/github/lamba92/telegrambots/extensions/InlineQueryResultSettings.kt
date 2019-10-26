package com.github.lamba92.telegrambots.extensions

data class InlineQueryResultSettings(
    var inlineQueryId: String,
    var cacheTime: Int = 1000,
    var isPersonal: Boolean = false
)