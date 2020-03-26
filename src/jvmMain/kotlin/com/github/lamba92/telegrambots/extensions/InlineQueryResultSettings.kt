package com.github.lamba92.telegrambots.extensions

data class InlineQueryResultSettings(
    var inlineQueryId: String,
    var cacheTime: Int = 0,
    var isPersonal: Boolean = false
)