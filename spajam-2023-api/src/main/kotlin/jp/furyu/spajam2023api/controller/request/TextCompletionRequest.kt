package jp.furyu.spajam2023api.controller.request

import com.fasterxml.jackson.annotation.JsonProperty

data class TextCompletionRequest(
        /**
         * ユーザの入力
         */
        val prompt: String,
        /**
         * 会話を特定するID 指定しない場合は新しい会話として扱う
         */
        @JsonProperty(required = false)
        val conversationId: String
)
