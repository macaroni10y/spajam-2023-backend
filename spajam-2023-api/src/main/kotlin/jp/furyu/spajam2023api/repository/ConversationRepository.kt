package jp.furyu.spajam2023api.repository

import jp.furyu.spajam2023api.domain.Conversation

/**
 * 会話の永続化を行う
 */
interface ConversationRepository {

    /**
     * 会話をconversationIdで検索する
     * @param conversationId 会話を一意に識別するID
     * @return 見つからない場合は新しい会話を返す
     */
    fun findByConversationId(conversationId: String): Conversation

    /**
     * 会話を保存する
     * @param conversation 会話
     */
    fun save(conversation: Conversation)

}