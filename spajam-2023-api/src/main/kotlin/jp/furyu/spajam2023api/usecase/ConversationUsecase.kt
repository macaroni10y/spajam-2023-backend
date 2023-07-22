package jp.furyu.spajam2023api.usecase

import jp.furyu.spajam2023api.domain.Conversation
import jp.furyu.spajam2023api.domain.OneChat
import jp.furyu.spajam2023api.domain.Speaker
import jp.furyu.spajam2023api.repository.ConversationRepository
import jp.furyu.spajam2023api.service.OpenAiService
import jp.furyu.spajam2023api.usecase.dto.ConversationDto
import org.springframework.stereotype.Component

@Component
class ConversationUsecase(private val openAiService: OpenAiService, private val conversationRepository: ConversationRepository) {
    suspend fun execute(prompt: String, conversationId: String): ConversationDto {
        val conversation = conversationRepository.findByConversationId(conversationId)
        val responseFromAi = openAiService.call(prompt, conversation)

        conversationRepository.save(
                conversation
                        .addChat(OneChat(Speaker.USER, prompt))
                        .addChat(OneChat(Speaker.AI, responseFromAi))
        )
        return ConversationDto(conversation.id, responseFromAi)
    }
}
