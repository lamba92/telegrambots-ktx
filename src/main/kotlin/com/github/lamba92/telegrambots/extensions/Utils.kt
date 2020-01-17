package com.github.lamba92.telegrambots.extensions

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.meta.generics.WebhookBot

/**
 * Creates and configures a [KtxTelegramLongPollingBot] using the [TelegrambotsDSL].
 */
fun buildBot(builder: KtxTelegramPollingBotBuilder.() -> Unit) =
    KtxTelegramPollingBotBuilder().apply(builder).build()

interface TelegramPollingBotProvider {
    val bot: LongPollingBot
}

interface TelegramWebhookBotProvider {
    val bot: WebhookBot
}

fun TelegramBotsApi.registerBot(botProvider: TelegramPollingBotProvider) =
    registerBot(botProvider.bot)!!

fun TelegramBotsApi.registerBot(botProvider: TelegramWebhookBotProvider) =
    registerBot(botProvider.bot)

@TelegrambotsInlineMessageBuilderDSL
fun buildInlineArticle(builder: InlineQueryResultArticle.() -> Unit) =
    InlineQueryResultArticle().apply(builder)

@TelegrambotsInlineMessageBuilderDSL
inline fun <reified T : InputMessageContent> InlineQueryResultArticle.content(builder: T.() -> Unit) {
    inputMessageContent = T::class.constructors.firstOrNull { it.parameters.isEmpty() }?.call()?.apply(builder)
        ?: throw IllegalArgumentException("${T::class.simpleName} has no empty arguments constructor!")
}

@DslMarker
annotation class TelegrambotsDSL

@DslMarker
annotation class TelegrambotsInlineMessageBuilderDSL

fun buildMarkdown(action: TelegramMarkdownBuilder.() -> Unit) =
    TelegramMarkdownBuilder().apply(action).toString()
