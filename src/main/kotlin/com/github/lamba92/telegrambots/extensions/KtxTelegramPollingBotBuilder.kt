package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.kodein.di.erased.instance
import org.kodein.di.erased.with
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent

@TelegrambotsDSL
class KtxTelegramPollingBotBuilder {

    /**
     * The API token used for authentication.
     */
    var botApiToken: String = ""

    /**
     * The BOT name.
     */
    var botUsername: String = ""

    private var kodeinBuilder: Kodein.MainBuilder.() -> Unit = {}
    private var handlersBuilder: MessageHandlersBuilder.() -> Unit = {}
    private var underlyingBotConfigurator: TelegramLongPollingBot.() -> Unit = {}

    /**
     * Allows to declare the message handlers.
     */
    @TelegrambotsDSL
    fun handlers(handler: MessageHandlersBuilder.() -> Unit) {
        handlersBuilder = handler
    }

    /**
     * Allows to configure the Kodein.
     */

    @TelegrambotsDSL
    fun kodein(builder: Kodein.MainBuilder.() -> Unit) {
        kodeinBuilder = builder
    }

    /**
     * Allows to configure the underlying [TelegramLongPollingBot]. The [action] is executed as the bot is created.
     */
    @TelegrambotsDSL
    fun underlyingBot(action: TelegramLongPollingBot.() -> Unit) {
        underlyingBotConfigurator = action
    }

    /**
     * Builds the bot!
     */
    fun build() =
        KtxTelegramLongPollingBot(
            botUsername,
            botApiToken,
            kodeinBuilder,
            MessageHandlersBuilder().apply(handlersBuilder).build(),
            underlyingBotConfigurator
        )

}

fun main() {
    val bot = buildBot {

        kodein {
            constant("mario") with "It's a me"
            constant("the answer") with 42
        }

        underlyingBot {

        }

        handlers {

            inlineQueries {

                val hello: String by instance(arg = "mario")

                if (inlineQuery.from.userName == "mario")
                    respond(
                        listOf(
                            buildInlineArticle {
                                content<InputTextMessageContent> {
                                    description = "This is actually the snippet"
                                    title = "Title of the single element"
                                    messageText = "what i write if you tap me"
//                                    id = hello
                                    thumbUrl = "https://logo.png"
                                }
                            })
                    )

            }

            messages {

                val number: Int by instance(arg = "the answer")

                if (message.from.userName == "mario")
                    respond {
                        // APIs here are WIP!
                    }
            }

        }

    }

}