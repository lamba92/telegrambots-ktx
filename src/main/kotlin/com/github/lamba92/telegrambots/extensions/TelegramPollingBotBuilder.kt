package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@TelegrambotsDSL
class TelegramPollingBotBuilder {

    var botApiToken: String = ""
    var botUsernameName: String = ""
    private var kodeinBuilder: (Kodein.MainBuilder.() -> Unit)? = null

    private var handlersBuilder: (MessageHandlersBuilder.() -> Unit)? = null

    @TelegrambotsDSL
    fun handlers(handler: MessageHandlersBuilder.() -> Unit) {
        handlersBuilder = handler
    }

    @TelegrambotsDSL
    fun kodein(builder: Kodein.MainBuilder.() -> Unit) {
        kodeinBuilder = builder
    }

    fun buildBot(): TelegramLongPollingBot = object : TelegramLongPollingBot(), KodeinAware {

        override val kodein by Kodein.lazy {
            kodeinBuilder?.invoke(this)
        }

        val handlers = MessageHandlersBuilder().apply {
            this@TelegramPollingBotBuilder.handlersBuilder?.invoke(this)
        }

        override fun getBotUsername() = botUsernameName

        override fun getBotToken() = botApiToken

        override fun onUpdateReceived(update: Update) {
            GlobalScope.launch {
                update.inlineQuery?.let {
                    handlers.inlineQueriesHandler
                        ?.inlineQueryHandler
                        ?.invoke(InlineQueryReceivedHandler(it.id, asExecutor(), kodein), it)
                }
            }
        }

    }

}