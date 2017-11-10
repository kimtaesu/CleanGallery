package com.hucet.clean.gallery.fixture

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import java.io.File

/**
 * Created by taesu on 2017-11-10.
 */
object DeserializerFixture {
    private val gson = GsonBuilder()
            .create()

    fun deserializeDirectory(path: String, parent: String): List<Directory> {
        val json = readJson("${parent}/${path}")
        return deserializeDirectory(json)
    }

    fun deserializeMedium(path: String, parent: String): List<Medium> {
        val json = readJson("${parent}/${path}")
        return deserializeMedium(json)
    }

    private fun readJson(path: String): String {
        val url = this.javaClass.classLoader.getResource("${path}")
        val file = File(url.file)
        return file.readText()
    }

    private fun deserializeMedium(json: String): List<Medium> {
        val fakeMediaType = object : TypeToken<List<Medium>>() {}.type
        return gson.fromJson<List<Medium>>(json, fakeMediaType)
    }

    private fun deserializeDirectory(json: String): List<Directory> {
        val fakeMediaType = object : TypeToken<List<Directory>>() {}.type
        return gson.fromJson<List<Directory>>(json, fakeMediaType)
    }
}
