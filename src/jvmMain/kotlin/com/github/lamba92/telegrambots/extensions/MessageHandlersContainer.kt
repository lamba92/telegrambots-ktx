package com.github.lamba92.telegrambots.extensions

data class MessageHandlersContainer(
    val inlineQueriesHandler: InlineQueryHandler,
    val sendMessageHandler: SendMessageHandler
)
