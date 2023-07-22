package jp.furyu.spajam2023api.usecase.dto

import jp.furyu.spajam2023api.controller.response.ConversationResponse

data class ConversationDto(val conversationId: String, val responseFromAi: String){
    fun toResponse() = ConversationResponse(conversationId, responseFromAi)
}
