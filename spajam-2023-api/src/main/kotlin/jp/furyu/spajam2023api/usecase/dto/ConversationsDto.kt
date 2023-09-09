package jp.furyu.spajam2023api.usecase.dto

import jp.furyu.spajam2023api.controller.response.AllConversationsResponse
import jp.furyu.spajam2023api.controller.response.Conversation

class ConversationsDto(private val conversations: List<OneChatDto>) {
    fun toResponse() = AllConversationsResponse(conversations.map { Conversation(it.speaker, it.text) })
}

class OneChatDto(val speaker: String, val text: String)
