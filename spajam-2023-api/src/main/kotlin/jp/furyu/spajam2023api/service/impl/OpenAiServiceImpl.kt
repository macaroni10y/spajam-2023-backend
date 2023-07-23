package jp.furyu.spajam2023api.service.impl

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import jp.furyu.spajam2023api.domain.Conversation
import jp.furyu.spajam2023api.service.OpenAiService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class OpenAiServiceImpl(private val openAI: OpenAI) : OpenAiService {
    private val logger: Logger = LoggerFactory.getLogger(OpenAiServiceImpl::class.java)

    @OptIn(BetaOpenAI::class)
    override suspend fun call(prompt: String, context: Conversation): String {
        val completionRequest = ChatCompletionRequest(
                model = ModelId("gpt-3.5-turbo"),
                messages = context.chats.map { ChatMessage(role = ChatRole(it.speaker.asSystemName()), content = it.text) }
                        + ChatMessage(role = ChatRole("user"), content = prompt),
        )
        val completion = openAI.chatCompletion(completionRequest)
        logger.info("completion  : {$completion}")
        return completion.choices.map { it.message }.map { it?.content }.joinToString(separator = "\n")
    }
}
