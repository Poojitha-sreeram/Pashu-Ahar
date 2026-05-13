package com.example.pashuahar

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryManager(
    context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "feed_history",
            Context.MODE_PRIVATE
        )

    private val gson = Gson()

    private val key = "history_list"

    /**
     * Save report
     */
    fun saveReport(item: FeedHistoryItem) {

        val currentList =
            getAllReports().toMutableList()

        // Avoid duplicate entries
        if (currentList.none { it.id == item.id }) {

            currentList.add(0, item)

            saveList(currentList)
        }
    }

    /**
     * Get all reports
     */
    fun getAllReports(): List<FeedHistoryItem> {

        val json =
            prefs.getString(key, null)
                ?: return emptyList()

        val type =
            object : TypeToken<List<FeedHistoryItem>>() {}.type

        return gson.fromJson(json, type)
    }

    /**
     * Delete single report
     */
    fun deleteReport(id: Long) {

        val updatedList =
            getAllReports()
                .filter { it.id != id }

        saveList(updatedList)
    }

    /**
     * Clear all reports
     */
    fun clearAllReports() {

        prefs.edit()
            .remove(key)
            .apply()
    }

    /**
     * Save updated list
     */
    private fun saveList(
        list: List<FeedHistoryItem>
    ) {

        val json =
            gson.toJson(list)

        prefs.edit()
            .putString(key, json)
            .apply()
    }
}