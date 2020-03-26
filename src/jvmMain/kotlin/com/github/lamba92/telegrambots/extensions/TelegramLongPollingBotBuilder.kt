package com.github.lamba92.telegrambots.extensions

import org.kodein.di.Kodein
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@TelegramBotsDSL
class TelegramLongPollingBotBuilder {

    /**
     * The API token used for authentication.
     */
    var botApiToken: String = ""

    /**
     * The BOT name.
     */
    var botUsername: String = ""

    private var kodeinBuilder: Kodein.MainBuilder.() -> Unit = {}
    private var handlersContainerBuilder: MessageHandlersBuilder.() -> Unit = {}
    private var underlyingBotConfigurator: TelegramLongPollingBot.() -> Unit = {}
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
    fun underlyingBot(action: TelegramLongPollingBot.() -> Unit) {
        underlyingBotConfigurator = action
    }

    /**
     * Allows to configure the [DefaultBotOptions] for the underlying [TelegramLongPollingBot].
     */
    @Suppress("unused")
    @TelegramBotsDSL
    fun TelegramLongPollingBot.options(action: DefaultBotOptions.() -> Unit) {
        botOptions.apply(action)
    }

    /**
     * Builds the bot!
     */
    fun build() =
        KtxTelegramLongPollingBot(
            botUsername,
            botApiToken,
            MessageHandlersBuilder().apply(handlersContainerBuilder).build(),
            botOptions,
            kodeinBuilder,
            allowKodeinOverride,
            underlyingBotConfigurator
        )

}
