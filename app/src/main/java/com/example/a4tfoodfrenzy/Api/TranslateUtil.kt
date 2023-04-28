package com.example.a4tfoodfrenzy.Api

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslateUtil {
    private val options =  TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.VIETNAMESE)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()
    private val translator = Translation.getClient(options)

    fun translate(text: String, onComplete: (result: String) -> Unit) {
        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { result ->
                        onComplete(result)
                    }
                    .addOnFailureListener { exception ->
                        onComplete("Translation failed: ${exception.message}")
                    }
            }
            .addOnFailureListener { exception ->
                onComplete("Model download failed: ${exception.message}")
            }
    }
}
