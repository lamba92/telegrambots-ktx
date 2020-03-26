package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery

class KtxTelegramLongPollingBot internal constructor(
    private val username: String,
    private val botApiToken: String,
    private val handlersContainer: MessageHandlersContainer,
    botOptions: DefaultBotOptions,
    kodeinBuilder: Kodein.MainBuilder.() -> Unit,
    allowKodeinOverride: Boolean,
    configurator: TelegramLongPollingBot.() -> Unit
) : TelegramLongPollingBot(botOptions), CoroutineScope, KodeinAware {

    init {
        configurator()
    }

    override val coroutineContext by lazy {
        SupervisorJob() + Dispatchers.Default
    }

    override val kodein by Kodein.lazy(allowKodeinOverride, kodeinBuilder)

    private fun newInlineQueryContext(inlineQuery: InlineQuery, kodein: Kodein) =
        InlineQueryContext(inlineQuery, coroutineInlineQueryExecutor, kodein)

    private fun newMessageContext(message: Message, kodein: Kodein) =
        MessageContext(message, coroutineSendMessageExecutor, kodein)

    override fun getBotUsername() =
        username

    override fun getBotToken() =
        botApiToken

    override fun onUpdateReceived(update: Update) = with(update) {

        if (hasInlineQuery())
            launch { handlersContainer.inlineQueriesHandler(newInlineQueryContext(inlineQuery, kodein)) }

        if (hasMessage())
            launch { handlersContainer.sendMessageHandler(newMessageContext(message, kodein)) }

    }

    override fun onClosing() {
        coroutineContext.cancel()
        super.onClosing()
    }

}
