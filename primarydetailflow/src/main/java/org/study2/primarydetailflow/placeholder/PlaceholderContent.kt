package org.study2.primarydetailflow.placeholder

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    init {
        addItem(PlaceholderItem("1","eBookFrenzy","https://www.ebookfrenzy.com"))
        addItem(PlaceholderItem("2","Amazon","https://www.amazon.com"))
        addItem(PlaceholderItem("3","New York Times","https://www.nytimes.com"))
    }



    private fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }




    /**
     * A placeholder item representing a piece of content.
     */
    data class PlaceholderItem(val id: String, val website_name: String, val website_url: String) {
        override fun toString(): String = website_name
    }
}