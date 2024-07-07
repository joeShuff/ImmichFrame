package uk.co.joeshuff.immichframe.util

fun String.findScheme(): String? {
    val prefixes = listOf("https://", "http://")

    prefixes.forEach {
        if (startsWith(it)) {
            return it
        }
    }

    return null
}

fun String.findResource(): String {
    val scheme = findScheme()
    var formatting = this

    scheme?.let {
        formatting = formatting.replace(it, "")
    }

    val port = findPort()

    port.let { formatting = formatting.replace(":$port", "") }

    return formatting
}

fun String.findPort(): String {
    return split(":").last()
}

fun String.toBaseUrl(): String {
    //If the stored string was provided with a scheme just return it
    val prefixes = listOf("https://", "http://")
    prefixes.forEach { prefix ->
        if (startsWith(prefix)) return this
    }

    //Default to have https scheme
    return "https://$this"
}