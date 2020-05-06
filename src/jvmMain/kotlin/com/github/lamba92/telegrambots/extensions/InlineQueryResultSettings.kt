package com.github.lamba92.telegrambots.extensions

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@OptIn(ExperimentalTime::class)
data class InlineQueryResultSettings(
    var inlineQueryId: String,
    var cacheTime: Duration = 0.seconds,
    var isPersonal: Boolean = false
)
