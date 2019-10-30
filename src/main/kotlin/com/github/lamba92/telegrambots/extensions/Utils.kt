package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.meta.generics.WebhookBot

fun TelegramLongPollingBot.asExecutor(): MessageExecutor = {
    withContext(IO) { execute(it) }
}

fun telegramBot(builder: TelegramPollingBotBuilder.() -> Unit) =
    TelegramPollingBotBuilder().apply(builder).buildBot()

interface TelegramPollingBotProvider {
    val bot: LongPollingBot
}

interface TelegramWebhookBotProvider {
    val bot: WebhookBot
}

fun TelegramBotsApi.registerBot(botProvider: TelegramPollingBotProvider) =
    registerBot(botProvider.bot).let { Unit }

fun TelegramBotsApi.registerBot(botProvider: TelegramWebhookBotProvider) =
    registerBot(botProvider.bot)

@TelegrambotsDSL
fun buildInlineArticle(builder: InlineQueryResultArticle.() -> Unit) =
    InlineQueryResultArticle().apply(builder)

@TelegrambotsDSL
inline fun <reified T : InputMessageContent> InlineQueryResultArticle.content(builder: T.() -> Unit) {
    inputMessageContent = T::class.constructors.first { it.parameters.isEmpty() }.call().apply(builder)
}

@DslMarker
annotation class TelegrambotsDSL
