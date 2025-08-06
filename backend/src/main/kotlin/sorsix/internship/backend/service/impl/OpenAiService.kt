package sorsix.internship.backend.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.util.retry.Retry
import java.time.Duration

@Service
class OpenAiService(
    @Value("\${openai.api.url}")
    private val openAiURL: String,
    @Value("\${openai.api.model}")
    private val openAiModel: String,
) {
    private val webClient = WebClient.builder()
        .baseUrl(openAiURL)
        .defaultHeader("Content-Type", "application/json")
        .build()

    fun getMoodScoreFromDescription(description: String): Int {
        if (description.isEmpty()) return 0
        val prompt = """
            Now, your job is to imitate a psychologist who will rate the mood for a sportsman. The sportsman is currently following
            a program, with recommendations from his sports medicine doctor, and the sportsman need to give his thoughts on how his mental health
            is developing or not during the process.
            You need to rate the following mood description on a scale from 1 (very negative) to 10 (very positive), as integer. 
            Only return the number, nothing else.
            Description: "$description"
        """.trimIndent()
        val requestBody = OpenAiRequest(
            model = openAiModel,
            messages = listOf(Message(role = "user", content = prompt))
        )
        return try {
            val response = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiResponse::class.java)
                .retryWhen(
                    Retry.backoff(1, Duration.ofSeconds(5))
                        .filter { it is WebClientResponseException.TooManyRequests }
                )
                .block()

            val content = response?.message?.content?.trim() ?: "0"
            content.toIntOrNull() ?: 0
        } catch (ex: WebClientResponseException) {
            println("HTTP Status: ${ex.statusCode}")
            println("Response body: ${ex.responseBodyAsString}")
            ex.printStackTrace()
            0
        }
    }

    data class OpenAiRequest(
        val model: String,
        val messages: List<Message>
    )

    data class OpenAiResponse(
        val message: Message
    )

    data class Message(
        val role: String,
        val content: String
    )
}


/*
Not a good mood:
Lately, I’ve been feeling overwhelmed and emotionally drained. I struggle to find motivation, and even small tasks feel like huge obstacles.
Despite trying to stay on track with my training, I constantly feel like I’m falling behind. My sleep has been poor, and I wake up feeling anxious more often than not.
 I don’t feel like I’m making progress, and it’s frustrating to see others moving forward while I feel stuck. There’s a lingering sense of hopelessness I can’t shake.
 I just feel tired — mentally, physically, emotionally — and I don’t know how to fix it.


Happy mood:
I feel light and joyful today! Everything seems brighter, and I can’t stop smiling.
It’s one of those days when even the little things — like sunshine, music, or a kind word — feel magical. I’m just genuinely happy to be alive and present.


Stressed mood:
I’m feeling overwhelmed and mentally drained today. There’s too much on my plate, and it feels like I can’t catch a break.
My thoughts are racing, and even small tasks seem exhausting. I just need some space to breathe and reset.
*/