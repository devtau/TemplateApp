package com.devtau.template.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class of apple to work with
 * @param id apple id
 * @param iconUrl url of apple icon
 * @param title apple title
 * @param subtitle apple subtitle
 */
@Entity(tableName = "Apples")
data class Apple(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "appleId")
    var id: Long?,
    val iconUrl: String,
    val title: String,
    val subtitle: String
) {

    /**
     * Provides string representation of apple name
     */
    fun getFormattedName(): String = when {
        title.isNotEmpty() && subtitle.isNotEmpty() -> "$title $subtitle"
        title.isNotEmpty() -> title
        else -> subtitle
    }

    fun isIconUrlEmpty() = iconUrl.isEmpty()

    companion object {

        /**
         * Provides one mock apple
         */
        fun getMock() = Apple(null, "https://picsum.photos/300/900", "next mock title", "next mock subtitle")

        /**
         * Provides mock list of apples imitating local data source
         */
        fun getMockLocalList() = mutableListOf(
            Apple(null, "https://picsum.photos/300/900", "first mock apple title", "first mock apple subtitle"),
            Apple(null, "https://picsum.photos/301/900", "second mock apple title", "second mock apple subtitle"),
            Apple(null, "https://picsum.photos/302/900", "third mock apple title", "third mock apple subtitle"),
            Apple(null, "https://picsum.photos/303/900", "fourth mock apple title", "fourth mock apple subtitle")
        )

        /**
         * Provides mock list of apples imitating remote data source
         */
        fun getMockRemoteList() = mutableListOf(
            Apple(null, "https://picsum.photos/300/900", "first mock apple title", "first mock apple subtitle"),
            Apple(null, "https://picsum.photos/301/900", "second mock apple title", "second mock apple subtitle"),
            Apple(null, "https://picsum.photos/302/900", "third mock apple title", "third mock apple subtitle"),
            Apple(null, "https://picsum.photos/303/900", "fourth mock apple title", "fourth mock apple subtitle"),
            Apple(null, "https://picsum.photos/304/900", "fifth mock apple title", "fifth mock apple subtitle")
        )
    }
}