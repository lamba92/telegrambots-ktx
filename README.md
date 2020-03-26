# TelegramBots KTX 
[![Build Status](https://travis-ci.org/lamba92/telegrambots-ktx.svg?branch=master)](https://travis-ci.org/lamba92/telegrambots-ktx) [ ![Download](https://api.bintray.com/packages/lamba92/com.github.lamba92/telegrambots-ktx/images/download.svg) ](https://bintray.com/lamba92/com.github.lamba92/telegrambots-ktx/_latestVersion)

`TelegramBots KTX` is a DSL wrapper for the [TelegramBots](https://github.com/rubenlagus/TelegramBots) library.

It is inspired by [Ktor](https://ktor.io)!

# Usage
A the moment only the polling bot is supported. The usage is pretty straightforward. use the `buildPollingBot()` function and le the DSL do the rest!
```kotlin
val bot = buildPollingBot {
    
    // customize those 2 and do not publish the token!
    botApiToken = "wifownf092392j2"
    botUsername = "botName"

    kodein { // Kodein.MainBuilder
        // kodein bindings
    }

    underlyingBot { // TelegramLongPollingBot
        // here the underlying bot is exposed
        options { //DefaultBotOptions 
            // Edit here the underlying bot 
            // initialization options 
        }
    }


    handlers {

        // this lambda is executed every time an inline
        // query is received 
        inlineQueries {

            // the respond method will send a response
            // to the user that made the request
            respond(emptyList()) { // InlineQueryResultSettings
                
            }
        }

        // this lambda is executed every time a message
        // is received 
        messages {

            // elaborate the data for your response here
            
            // the respond method will send a response
            // to the user that made the request
            respond { // SendMessage
                // Configure your response here
            }
        }
    }

}

fun main() {
    // this will start the bot!
    val botSession = KApiContextInitializer {
        registerBot(bot)
    }
}
```
The contexts are `KodeinAware` so you can retrieve you stuff wherever you want!

PRO TIP 1: both `InlineQueryContext` and `MessageContext` contains all that is needed to respond, so fell free to refactor your logic out of there with an extension function!

PRO TIP 2: there are also commodity DSLs to build markdown using `buildMarkdown` and an inline message content using `buildInlineArticle`. Try them out!  

# Installation [ ![Download](https://api.bintray.com/packages/lamba92/com.github.lamba92/telegrambots-ktx/images/download.svg) ](https://bintray.com/lamba92/com.github.lamba92/telegrambots-ktx/_latestVersion)
```kotlin
repositories {
    jcenter()
}
...
dependencies {
    implementation("com.github.lamba92", "ktor-spa", "{latest_version}")
}
```
