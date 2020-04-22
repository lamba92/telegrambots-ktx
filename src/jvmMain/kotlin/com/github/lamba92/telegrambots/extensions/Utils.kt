package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle

/**
 * Creates and configures a [KtxTelegramLongPollingBot] using the [TelegramBotsDSL].
 */
fun buildPollingBot(builder: TelegramLongPollingBotBuilder.() -> Unit): TelegramLongPollingBot =
    TelegramLongPollingBotBuilder().apply(builder).build()

@TelegramBotsDSL
fun buildInlineArticle(builder: InlineQueryResultArticle.() -> Unit) =
    InlineQueryResultArticle().apply(builder)

@TelegramBotsDSL
inline fun <reified T : InputMessageContent> InlineQueryResultArticle.content(builder: T.() -> Unit) {
    inputMessageContent = T::class.constructors.firstOrNull { it.parameters.isEmpty() }?.call()?.apply(builder)
        ?: throw IllegalArgumentException("${T::class.simpleName} has no empty arguments constructor!")
}

@DslMarker
internal annotation class TelegramBotsDSL

fun buildMarkdown(action: TelegramMarkdownBuilder.() -> Unit) =
    TelegramMarkdownBuilder().apply(action).toString()

internal val DefaultAbsSender.coroutineInlineQueryExecutor: InlineQueryExecutor
    get() = {
        withContext(Dispatchers.IO) { execute(it) }
    }

internal val DefaultAbsSender.coroutineSendMessageExecutor: SendMessageExecutor
    get() = {
        withContext(Dispatchers.IO) { execute(it) }
    }

val bot = buildPollingBot {

    kodein {

    }

    underlyingBot {
        options {

        }
    }

    handlers {
        inlineQueries {
            respond(emptyList()) {

            }
        }
        messages {
            respond {

            }
        }
    }

}

val InlineQuery.text
    get() = query!!
