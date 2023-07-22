package jp.furyu.spajam2023api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI


@Configuration
class DynamoDbConfig(@param:Value("\${aws.dynamodb.endpoint}") private val dynamoDbEndPointUrl: String) {

    @get:Bean
    @get:Profile("local")
    val dynamoDbClientLocal: DynamoDbClient
        get() = DynamoDbClient.builder()
                .endpointOverride(URI.create(dynamoDbEndPointUrl))
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("fake_access_key", "fake_secret_access_key")))
                .build()

    @get:Bean
    @get:Profile("!local")
    val dynamoDbClient: DynamoDbClient
        get() = DynamoDbClient.builder()
                .region(Region.AP_NORTHEAST_1)
                .build()
}
