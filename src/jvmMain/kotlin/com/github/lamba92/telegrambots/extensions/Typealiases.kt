package com.github.lamba92.telegrambots.extensions

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

typealias InlineQueryHandler = suspend InlineQueryContext.() -> Unit
typealias SendMessageHandler = suspend MessageContext.() -> Unit
typealias InlineQueryExecutor = suspend (AnswerInlineQuery) -> Unit
typealias SendMessageExecutor = suspend (SendMessage) -> Unit

