package com.github.lamba92.telegrambots.extensions

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery

typealias InlineQueryInterceptor = suspend InlineQueryReceivedHandler.(InlineQuery) -> Unit
typealias InlineQuerySelectionInterceptor = suspend InlineQuerySelectionHandler.(ChosenInlineQuery) -> Unit
typealias MessageExecutor = suspend (AnswerInlineQuery) -> Boolean