package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery

class KtxTelegramLongPollingBot internal constructor(
    private val username: String,
    private val botApiToken: String,
    private val kodeinBuilder: Kodein.MainBuilder.() -> Unit,
    private val handlers: MessageHandlers,
    configurator: TelegramLongPollingBot.() -> Unit
) : TelegramLongPollingBot(), CoroutineScope, KodeinAware {

    init {
        configurator()
    }

    override val coroutineContext by lazy {
        SupervisorJob() + Dispatchers.Default
    }

    override val kodein by Kodein.lazy {
        kodeinBuilder()
    }

    private val coroutineInlineQueryExecutor: InlineQueryExecutor
        get() = {
            withContext(Dispatchers.IO) { execute(it) }
        }

    private val coroutineSendMessageExecutor: SendMessageExecutor
        get() = {
            withContext(Dispatchers.IO) { execute(it) }
        }

    override fun getBotUsername() =
        username

    override fun getBotToken() =
        botApiToken

    override fun onUpdateReceived(update: Update) = with(update) {

        if (hasInlineQuery())
            newInlineQueryContext(inlineQuery, kodein) context@{
                launch { handlers.inlineQueriesHandler(this@context) }
            }

        if (hasMessage())
            newMessageContext(message, kodein) context@{
                launch { handlers.sendMessageHandler(this@context) }
            }

    }

    override fun onClosing() {
        coroutineContext.cancel()
        super.onClosing()
    }

    private fun newInlineQueryContext(
        inlineQuery: InlineQuery,
        kodein: Kodein,
        action: InlineQueryContext.() -> Unit = {}
    ) = InlineQueryContext(inlineQuery, coroutineInlineQueryExecutor, kodein)
        .apply(action)

    private fun newMessageContext(
        message: Message,
        kodein: Kodein,
        action: SendMessageContext.() -> Unit
    ) = SendMessageContext(message, coroutineSendMessageExecutor, kodein)
        .apply(action)

}

