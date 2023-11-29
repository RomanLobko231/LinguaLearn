package com.example.lingualearn.utils

import com.example.lingualearn.data.LearnedWord
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

object LearnedWordSerializer : KSerializer<LearnedWord> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("com.example.lingualearn") {
            element<String>("uaWord")
            element<String>("esWord")
        }


    override fun serialize(encoder: Encoder, value: LearnedWord): Unit =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, index = 0, value.uaWord)
            encodeStringElement(descriptor, index = 1, value.esWord)
        }

    override fun deserialize(decoder: Decoder): LearnedWord = decoder.decodeStructure(descriptor) {
        var uaWord: String? = null
        var esWord: String? = null
        loop@ while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                DECODE_DONE -> break@loop
                0 -> {
                    uaWord = decodeStringElement(descriptor, index = 0)
                }
                1 -> {
                    esWord = decodeStringElement(descriptor, index = 1)
                }
                else -> throw SerializationException("Unexpected index $index")
            }
        }
        LearnedWord(uaWord?: "error", esWord?:"error")
    }
}