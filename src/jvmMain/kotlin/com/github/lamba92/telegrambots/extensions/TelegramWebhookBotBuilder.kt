@file:Suppress("unused")

package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.bots.TelegramWebhookBot

@TelegramBotsDSL
class TelegramWebhookBotBuilder {

    /**
     * The API token used for authentication.
     */
    var botApiToken: String = ""

    /**
     * The BOT name.
     */
    var botUsername: String = ""

    /**
     * Mysterious undocumented parameter. The only example on how to use it is
     * [here](https://github.com/rubenlagus/TelegramBotsExample/blob/master/src/main/java/org/telegram/updateshandlers/WebHookExampleHandlers.java#L36-L39)
     * but it's a toy example. Please open an issue [here](https://github.com/lamba92/telegrambots-ktx/issues)
     * if you find out what it is!
     */
    var botPath: String = ""

    private var kodeinBuilder: Kodein.MainBuilder.() -> Unit = {}
    private var handlersContainerBuilder: MessageHandlersBuilder.() -> Unit = {}
    private var underlyingBotConfigurator: TelegramWebhookBot.() -> Unit = {}
    private var allowKodeinOverride: Boolean = false
    private var botOptions: DefaultBotOptions = DefaultBotOptions()

    /**
     * Allows to declare the message handlers.
     */
    @TelegramBotsDSL
    fun handlers(handler: MessageHandlersBuilder.() -> Unit) {
        handlersContainerBuilder = handler
    }

    /**
     * Allows to configure the Kodein container.
     * Creates a [Kodein] instance that will be lazily created upon first access.
     *
     * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
     * @param init The block of configuration.
     * @return A lazy property that will yield, when accessed, the new Kodein object, freshly created, and ready for hard work!
     */
    @TelegramBotsDSL
    fun kodein(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit) {
        allowKodeinOverride = allowSilentOverride
        kodeinBuilder = init
    }

    /**
     * Allows to configure the underlying [TelegramLongPollingBot]. The [action] is executed as the bot is created.
     */
    @TelegramBotsDSL
    fun underlyingBot(action: TelegramWebhookBot.() -> Unit) {
        underlyingBotConfigurator = action
    }

    /**
     * Allows to configure the [DefaultBotOptions] for the underlying [TelegramLongPollingBot].
     */
    @TelegramBotsDSL
    fun TelegramLongPollingBot.options(action: DefaultBotOptions.() -> Unit) {
        botOptions.apply(action)
    }

    /**
     * Builds the bot!
     */
    fun build(): Nothing = TODO()
//        KtxTelegramWebhookBot(
//            botUsername,
//            botApiToken,
//            MessageHandlersBuilder().apply(handlersContainerBuilder).build(),
//            botOptions,
//            kodeinBuilder,
//            allowKodeinOverride,
//            underlyingBotConfigurator
//        )

}
