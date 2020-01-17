package com.github.lamba92.telegrambots.extensions

@TelegrambotsDSL
class MessageHandlersBuilder {

    private var inlineQueriesHandler: InlineQueryHandler = {}
    private var sendMessageHandler: SendMessageHandler = {}

    @TelegrambotsDSL
    fun inlineQueries(handler: InlineQueryHandler) {
        inlineQueriesHandler = handler
    }

    @TelegrambotsDSL
    fun messages(handler: SendMessageHandler) {
        sendMessageHandler = handler
    }

    internal fun build() =
        MessageHandlers(inlineQueriesHandler, sendMessageHandler)

}