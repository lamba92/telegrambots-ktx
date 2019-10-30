package com.github.lamba92.telegrambots.extensions

@TelegrambotsDSL
class MessageHandlersBuilder {

    internal var inlineQueriesHandler: InlineQueriesHandler? = null

    @TelegrambotsDSL
    fun inlineQueries(handler: InlineQueriesHandler.() -> Unit) {
        inlineQueriesHandler = InlineQueriesHandler().apply(handler)
    }

}