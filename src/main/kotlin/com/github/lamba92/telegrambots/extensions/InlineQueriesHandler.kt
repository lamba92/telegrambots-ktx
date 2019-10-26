package com.github.lamba92.telegrambots.extensions

class InlineQueriesHandler {

    internal var inlineQueryHandler: InlineQueryInterceptor? = null
    internal var inlineSelectionHandler: InlineQuerySelectionInterceptor? = null

    fun handleQuery(action: InlineQueryInterceptor) {
        inlineQueryHandler = action
    }

    fun handleSelection(action: InlineQuerySelectionInterceptor) {
        inlineSelectionHandler = action
    }

}