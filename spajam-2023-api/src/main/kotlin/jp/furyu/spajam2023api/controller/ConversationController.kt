package jp.furyu.spajam2023api.controller

import jp.furyu.spajam2023api.controller.request.TextCompletionRequest
import jp.furyu.spajam2023api.controller.response.AllConversationsResponse
import jp.furyu.spajam2023api.controller.response.Conversation
import jp.furyu.spajam2023api.repository.ConversationRepository
import jp.furyu.spajam2023api.usecase.ConversationUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class ConversationController(private val conversationUsecase: ConversationUsecase, private val conversationRepository: ConversationRepository) {

    @GetMapping("/conversation")
    fun conversation(conversationId: String): ResponseEntity<AllConversationsResponse> =
            ResponseEntity.ok(
                    conversationRepository.findByConversationId(conversationId).let { conversation ->
                        AllConversationsResponse(
                                conversation.id,
                                conversation.chats.map { Conversation(it.speaker.asSystemName(), it.text) }
                        )
                    }
            )

    @PostMapping("/conversation")
    suspend fun conversation(@RequestBody request: TextCompletionRequest): ResponseEntity<Any> {
        request.needAllHistory?.let { if (it) return ResponseEntity.ok(conversationUsecase.executeWithAllHistories(request.prompt, request.conversationId).toResponse()) }
        return ResponseEntity.ok(conversationUsecase.execute(request.prompt, request.conversationId).toResponse())
    }
}
