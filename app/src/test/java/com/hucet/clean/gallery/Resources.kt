package com.hucet.clean.gallery

import java.io.File
import java.net.URI

internal object Resources

fun resource(name: String): URI {
    val explicitName = if (name.startsWith("/")) name else "/$name"
    Resources::javaClass::class.java.getResource(explicitName)
    val resource = Resources::class.java.getResource(explicitName)
    requireNotNull(resource) { "Make sure the resource '$name' exists!" }
    return resource.toURI()
}

fun resourceAsString(name: String): String = File(resource(name)).readText()
