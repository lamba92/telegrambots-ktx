package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult

class InlineQueryReceivedHandler(
    private val defaultQueryId: String,
    private val executor: MessageExecutor,
    override val kodein: Kodein
) : KodeinAware {

    fun respond(
        response: List<InlineQueryResult>,
        customSetup: InlineQueryResultSettings.() -> Unit = {}
    ) = answer {
        val settings = InlineQueryResultSettings(defaultQueryId).apply(customSetup)
        isPersonal = settings.isPersonal
        cacheTime = settings.cacheTime
        inlineQueryId = settings.inlineQueryId
        results = response
    }

    private fun answer(action: AnswerInlineQuery.() -> Unit) =
        executor(AnswerInlineQuery().apply(action))

}