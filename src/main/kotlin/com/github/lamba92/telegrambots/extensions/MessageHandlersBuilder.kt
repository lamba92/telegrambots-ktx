package com.github.lamba92.telegrambots.extensions

class MessageHandlersBuilder() {

    internal var inlineQueriesHandler: InlineQueriesHandler? = null

    fun inlineQueries(handler: InlineQueriesHandler.() -> Unit) {
        inlineQueriesHandler = InlineQueriesHandler().apply(handler)
    }

}