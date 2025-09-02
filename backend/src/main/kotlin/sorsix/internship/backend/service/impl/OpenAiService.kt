package sorsix.internship.backend.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.util.retry.Retry
import sorsix.internship.backend.dto.RecommendationResponse
import java.time.Duration

@Service
class OpenAiService(
    @Value("\${openai.api.url}")
    private val openAiURL: String,
    @Value("http://localhost:11434/api/generate")
    private val summaryURL: String,
    @Value("\${openai.api.model}")
    private val openAiModel: String,
) {
    private val webClient = WebClient.builder()
        .baseUrl(openAiURL)
        .defaultHeader("Content-Type", "application/json")
        .build()

    private val testWebClient = WebClient.builder()
        .baseUrl(summaryURL)
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
            ex.printStackTrace()
            0
        }
    }

    fun summarizeRecommendations(recommendations: List<RecommendationResponse>): String {
        if (recommendations.isEmpty()) return ""

        val promptBuilder = StringBuilder()
        promptBuilder.appendLine(
            """
        You are an expert sports-medicine summary writer. Your job is to read a list of clinical recommendations provided by a sports medicine team and produce a professional, clinically-minded written summary intended for:
        1) the patient (clear, actionable highlights) and
        2) the treating clinician (concise clinical notes and flags).
        
        Instructions:
        - Start with a short overview (2–4 sentences) describing overall themes, major priorities, estimated monthly cost total, and any potential conflicts or important cautions.
        - Then produce a separate section for each recommendation using it's label property, something like "<Recommendation's label>: ". Make sure you separate them so they aren't one continuous text!
        - End with a short "Action plan" (as many bullet points as you deem worthy) summarizing next steps across all recommendations.
        
        Formatting rules:
        - Format the text using HTML, as it shall be displayed on a frontend application using innerHTML.
        - Keep language professional, concise, and actionable.
        - Do NOT include anything outside the requested summary (no meta commentary, no explanation of how you generated the summary).
        - Do NOT repeat the recommendations back to me, just give your professional summary of them and any other advice you deem worthy from your professional experience.
        """.trimIndent()
        )

        recommendations.forEachIndexed { idx, r ->
            val num = idx + 1
            promptBuilder.appendLine()
            promptBuilder.appendLine("Recommendation $num:")
            promptBuilder.appendLine("type: ${r.type}")
            promptBuilder.appendLine("restrictionLevel: ${r.restrictionLevel}")
            promptBuilder.appendLine("label: ${r.label}")
            promptBuilder.appendLine("description: \"\"\"${r.description.trim()}\"\"\"")
            promptBuilder.appendLine("costPerMonth: ${r.costPerMonth}")
            promptBuilder.appendLine("durationWeeks: ${r.durationWeeks}")
            promptBuilder.appendLine("frequencyPerDay: ${r.frequencyPerDay}")
            promptBuilder.appendLine("targetGoal: ${r.targetGoal}")
            promptBuilder.appendLine("effectivenessRating: ${r.effectivenessRating}")
            promptBuilder.appendLine("doctorPersonalizedNotes: \"\"\"${r.doctorPersonalizedNotes.trim()}\"\"\"")
        }

        promptBuilder.appendLine()
        promptBuilder.appendLine("Return only the requested professional summary following the instructions above. Nothing else. Again, just format it with HTML.")
        promptBuilder.appendLine()
        promptBuilder.appendLine("IMPORTANT: Do NOT output only the word 'Overview' or any single-word headings.")
        promptBuilder.appendLine("Start immediately with the 2-4 sentence overview (no standalone heading), then continue with the specifics.")
        promptBuilder.appendLine()

        val prompt = promptBuilder.toString()

        val requestBody = mapOf(
            "model" to openAiModel,
            "prompt" to prompt,
            "max_tokens" to 1024,
            "temperature" to 0.1,
            "stream" to false
        )

        return try {
            val raw = testWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiResponse2::class.java)
                .block()
            raw?.response?.trim() ?: ""
        } catch (ex: WebClientResponseException) {
            ex.printStackTrace()
            "Couldn't generate summary"
        }
    }

    data class OpenAiRequest(
        val model: String,
        val messages: List<Message>
    )

    data class OpenAiResponse(
        val message: Message
    )

    data class OpenAiResponse2(
        val model: String,
        val created_at: String,
        val response: String?,
        val done: Boolean,
        val done_reason: String?,
        val context: List<Long>?,
        val total_duration: Long?,
        val load_duration: Long?,
        val prompt_eval_count: Int?,
        val prompt_eval_duration: Long?,
        val eval_count: Int?,
        val eval_duration: Long?
    )

    data class Message(
        val role: String,
        val content: String
    )
}