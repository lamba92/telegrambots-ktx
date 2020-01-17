package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult

@TelegrambotsDSL
class InlineQueryContext(
    val inlineQuery: InlineQuery,
    private val executor: InlineQueryExecutor,
    override val kodein: Kodein
) : KodeinAware {

    suspend fun respond(
        response: List<InlineQueryResult>,
        customSettings: InlineQueryResultSettings.() -> Unit = {}
    ) = respond {
        val settings =
            InlineQueryResultSettings(inlineQuery.id).apply(customSettings)
        isPersonal = settings.isPersonal
        cacheTime = settings.cacheTime
        inlineQueryId = settings.inlineQueryId
        results = response
    }

    private suspend fun respond(action: AnswerInlineQuery.() -> Unit) =
        executor(AnswerInlineQuery().apply(action))

}