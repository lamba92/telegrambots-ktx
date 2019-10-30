package com.github.lamba92.telegrambots.extensions

@TelegrambotsDSL
class InlineQueriesHandler {

    internal var inlineQueryHandler: InlineQueryInterceptor? = null
    internal var inlineSelectionHandler: InlineQuerySelectionInterceptor? = null

    @TelegrambotsDSL
    fun handleQuery(action: InlineQueryInterceptor) {
        inlineQueryHandler = action
    }

    @TelegrambotsDSL
    fun handleSelection(action: InlineQuerySelectionInterceptor) {
        inlineSelectionHandler = action
    }

}