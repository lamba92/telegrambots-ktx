package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

class SendMessageContext(
    val message: Message,
    private val coroutineExecutor: SendMessageExecutor,
    override val kodein: Kodein
) : KodeinAware {

    suspend fun respond(messageBuilder: SendMessage.() -> Unit) =
        respond(SendMessage().setChatId(message.chatId).apply(messageBuilder))

    private suspend fun respond(message: SendMessage) =
        coroutineExecutor(message)

}