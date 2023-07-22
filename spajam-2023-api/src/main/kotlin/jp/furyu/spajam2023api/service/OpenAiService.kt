package jp.furyu.spajam2023api.service

import jp.furyu.spajam2023api.domain.Conversation

interface OpenAiService {
    /**
     * OpenAI APIを呼び出す
     * @param prompt ユーザの入力
     * @param context 会話のコンテキスト
     * @return OpenAI APIの出力
     */
    suspend fun call(prompt: String, context: Conversation): String
}