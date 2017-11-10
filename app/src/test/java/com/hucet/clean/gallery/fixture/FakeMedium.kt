package com.hucet.clean.gallery.fixture

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hucet.clean.gallery.model.Medium
import java.io.File

/**
 * Created by taesu on 2017-11-10.
 */
object FakeMedium {

    private val gson = GsonBuilder()
            .create()

    fun deserializeResource(path: String): List<Medium> {
        val json = readJson(path)
        return deserialize(json)
    }

    private fun readJson(path: String, parent: String = "media"): String {
        val url = this.javaClass.classLoader.getResource("${parent}/${path}")
        val file = File(url.file)
        return file.readText()
    }

    private fun deserialize(json: String): List<Medium> {
        val fakeMediaType = object : TypeToken<List<Medium>>() {}.type
        return gson.fromJson<List<Medium>>(json, fakeMediaType)
    }
}
