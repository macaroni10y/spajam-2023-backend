package jp.furyu.spajam2023api.repository

import jp.furyu.spajam2023api.domain.Conversation
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

@Component
class ConversationRepositoryImpl(private val client: DynamoDbClient) : ConversationRepository {
    private val logger = org.slf4j.LoggerFactory.getLogger(ConversationRepositoryImpl::class.java)
    companion object {
        const val TABLE_NAME = "Conversation"
    }

    override fun findByConversationId(conversationId: String): Conversation {
        val keyToGet: HashMap<String, AttributeValue> = HashMap()
        keyToGet["conversationId"] = AttributeValue.builder().s(conversationId).build()
        try {
            val response = client.getItem { it.key(keyToGet).tableName(TABLE_NAME) }
            if (response.item().isEmpty()) {
                return Conversation.create()
            }
            val json = response.item()["value"]!!.s().replace("\n", "")

            logger.info("got conversation items: $json.")

            return Conversation.fromJson(json)
        } catch (e: DynamoDbException) {
            logger.info("Failed to get conversation:  $conversationId. create new conversation.")
            return Conversation.create()
        }
    }

    override fun save(conversation: Conversation) {
        val itemValues: HashMap<String, AttributeValue> = HashMap()
        itemValues["conversationId"] = AttributeValue.builder().s(conversation.id).build()
        itemValues["value"] = AttributeValue.builder().s(conversation.toJson()).build()

        val putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build()
        try {
            client.putItem(putItemRequest)
        } catch (e: DynamoDbException) {
            logger.error("Failed to save conversation: $conversation")
            throw RuntimeException(e)
        }
    }
}