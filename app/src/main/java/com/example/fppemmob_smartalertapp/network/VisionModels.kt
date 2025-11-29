package com.example.fppemmob_smartalertapp.network

import com.google.gson.annotations.SerializedName

// ==========================================
// REQUEST MODELS (Apa yang kita kirim ke Google)
// ==========================================

data class VisionRequest(
    @SerializedName("requests")
    val requests: List<AnnotateImageRequest>
)

data class AnnotateImageRequest(
    @SerializedName("image")
    val image: ImageContent,
    @SerializedName("features")
    val features: List<Feature>
)

data class ImageContent(
    @SerializedName("content")
    val content: String // Ini nanti isinya gambar dalam format Base64 String
)

data class Feature(
    @SerializedName("type")
    val type: String = "LABEL_DETECTION", // Kita minta Google mendeteksi Label (objek)
    @SerializedName("maxResults")
    val maxResults: Int = 5
)

// ==========================================
// RESPONSE MODELS (Apa yang Google jawab)
// ==========================================

data class VisionResponse(
    @SerializedName("responses")
    val responses: List<AnnotateImageResponse>?
)

data class AnnotateImageResponse(
    @SerializedName("labelAnnotations")
    val labelAnnotations: List<LabelAnnotation>?,
    @SerializedName("error")
    val error: VisionError?
)

data class LabelAnnotation(
    @SerializedName("mid")
    val mid: String?,
    @SerializedName("description")
    val description: String?, // Contoh: "Car crash", "Fire", "Vehicle"
    @SerializedName("score")
    val score: Float?, // Tingkat keyakinan (0.0 - 1.0)
    @SerializedName("topicality")
    val topicality: Float?
)

data class VisionError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)