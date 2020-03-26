package com.github.lamba92.telegrambots.extensions

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery

@Suppress("UNUSED_PARAMETER")
class KtxTelegramWebhookBot internal constructor(
    private val username: String,
    private val botApiToken: String,
    private val path: String,
    private val handlersContainer: MessageHandlersContainer,
    botOptions: DefaultBotOptions,
    kodeinBuilder: Kodein.MainBuilder.() -> Unit,
    allowKodeinOverride: Boolean,
    configurator: TelegramWebhookBot.() -> Unit
) : TelegramWebhookBot(botOptions), KodeinAware {

    init {
        configurator()
        TODO("This class need to be finished")
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

    override fun getBotPath() =
        path

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*>? = with(update) {
        runBlocking {
            if (hasInlineQuery())
                handlersContainer.inlineQueriesHandler(newInlineQueryContext(inlineQuery, kodein))

            if (hasMessage())
                launch { handlersContainer.sendMessageHandler(newMessageContext(message, kodein)) }
        }

        null
    }

}
