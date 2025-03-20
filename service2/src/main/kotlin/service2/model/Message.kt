package service2.model

import java.io.Serializable
import java.util.*

data class Message(
        var content: String? = null,
        var sender: String? = null,
        var timestamp: Date = Date()
) : Serializable