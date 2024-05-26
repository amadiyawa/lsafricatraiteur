package com.amadiyawa.feature_delivery.data.util

import android.content.Context
import android.content.res.Resources
import com.amadiyawa.feature_delivery.R
import com.amadiyawa.feature_delivery.domain.model.Menu
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

internal fun loadMenuList(context: Context): List<Menu> {
    return try {
        val inputStream = context.resources.openRawResource(R.raw.menus_fr)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()

        Json.decodeFromString<List<Menu>>(json)
    } catch (e: IOException) {
        emptyList()
    } catch (e: Resources.NotFoundException) {
        emptyList()
    }
}