package com.github.lamba92.telegrambots.extensions

data class MessageHandlers(
    val inlineQueriesHandler: InlineQueryHandler,
    val sendMessageHandler: SendMessageHandler
)