package jp.furyu.spajam2023api.config

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.Exception
import kotlin.time.Duration.Companion.seconds

@Configuration
class OpenAIConfig {
    @Bean
    fun openAi(@Value("\${openai.token}") token: String): OpenAI {
        try {
            return OpenAI(
                    token = token,
                    timeout = Timeout(socket = 20.seconds),
            )
        } catch (e: Exception){
            e.printStackTrace()
        }
        return OpenAI(
                token = token,
                timeout = Timeout(socket = 20.seconds),
        )
    }
}
