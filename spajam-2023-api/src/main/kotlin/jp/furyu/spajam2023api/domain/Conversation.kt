package jp.furyu.spajam2023api.domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ulid.ULID

/**
 * 会話履歴を表す
 */
@Serializable
data class Conversation(val id: String, val chats: List<OneChat>) {
    companion object {
        /**
         * 新しい会話を作成する
         */
        fun create(): Conversation = Conversation(ULID.randomULID(), listOf())

        /**
         * JSONから会話を作成する
         */
        fun fromJson(json: String): Conversation = Json.decodeFromString<Conversation>(json)
    }

    /**
     * 会話を追加する
     */
    fun addChat(chat: OneChat): Conversation = Conversation(id, chats + chat)

    fun toJson(): String = Json.encodeToString<Conversation>(this)
}

@Serializable
data class OneChat(val speaker: Speaker, val text: String) {
    fun toJson(): String = Json.encodeToString<OneChat>(this)
}

@Serializable
enum class Speaker {
    USER,
    AI;

    fun asSystemName(): String = when (this) {
        USER -> "user"
        AI -> "assistant"
    }
}
