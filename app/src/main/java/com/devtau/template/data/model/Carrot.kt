package com.devtau.template.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class of carrot to work with
 * @param id carrot id
 * @param iconUrl url of carrot icon
 * @param title carrot title
 * @param subtitle carrot subtitle
 */
@Entity(tableName = "Carrots")
data class Carrot(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "carrotId")
    var id: Long?,
    val iconUrl: String,
    val title: String,
    val subtitle: String
) {

    /**
     * Provides string representation of carrot name
     */
    fun getFormattedName(): String = when {
        title.isNotEmpty() && subtitle.isNotEmpty() -> "$title $subtitle"
        title.isNotEmpty() -> title
        else -> subtitle
    }

    companion object {

        /**
         * Provides one mock carrot
         */
        fun getMock() = Carrot(null, "https://picsum.photos/300/900", "next mock title", "next mock subtitle")

        /**
         * Provides mock list of carrots
         */
        fun getMockList(): List<Carrot> = listOf(
            Carrot(null, "https://picsum.photos/300/900", "first mock title", "first mock subtitle"),
            Carrot(null, "https://picsum.photos/301/900", "second mock title", "second mock subtitle"),
            Carrot(null, "https://picsum.photos/302/900", "third mock title", "third mock subtitle"),
            Carrot(null, "https://picsum.photos/303/900", "fourth mock title", "fourth mock subtitle"),
            Carrot(null, "https://picsum.photos/304/900", "fifth mock title", "fifth mock subtitle")
        )
    }
}