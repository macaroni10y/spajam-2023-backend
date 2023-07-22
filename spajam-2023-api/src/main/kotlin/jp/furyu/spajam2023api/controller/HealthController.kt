package jp.furyu.spajam2023api.controller

import jp.furyu.spajam2023api.controller.request.TextCompletionRequest
import jp.furyu.spajam2023api.controller.response.ConversationResponse
import jp.furyu.spajam2023api.usecase.ConversationUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(private val conversationUsecase: ConversationUsecase) {
    @GetMapping("/health")
    fun health(): String = "OK"

    @PostMapping("/conversation")
    suspend fun conversation(@RequestBody textCompletionRequest: TextCompletionRequest): ResponseEntity<ConversationResponse> =
            ResponseEntity.ok(conversationUsecase.execute(textCompletionRequest.prompt, textCompletionRequest.conversationId).toResponse())
}
