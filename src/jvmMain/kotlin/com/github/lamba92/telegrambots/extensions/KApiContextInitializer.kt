package com.github.lamba92.telegrambots.extensions

import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi

object KApiContextInitializer : TelegramBotsApi() {

    init {
        ApiContextInitializer.init()
    }

    operator fun <T> invoke(action: KApiContextInitializer.() -> T) =
        this.action()

}
