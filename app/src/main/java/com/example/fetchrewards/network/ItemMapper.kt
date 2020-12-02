package com.example.fetchrewards.network

import androidx.annotation.VisibleForTesting
import com.example.fetchrewards.NAME_SEPARATOR
import com.example.fetchrewards.database.RoomItem
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemMapper @Inject constructor() {

    fun map(remoteItem: RemoteItem): RoomItem {
        val nameNumber = parseNumber(remoteItem.name)
        return RoomItem(
            id = remoteItem.id ?: 0,
            listId = remoteItem.listId ?: 0,
            nameFormat = getNameFormat(remoteItem.name, nameNumber),
            nameNumber = nameNumber
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun parseNumber(name: String?): Int? {
        name?.forEachIndexed { index, character ->
            if (character.isDigit()) {
                val builder = StringBuilder()
                builder.append(character)

                var i = index + 1
                while (i < name.length && name[i].isDigit()) {
                    builder.append(name[i])
                    i++
                }
                return Integer.parseInt(builder.toString())
            }
        }

        return null
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNameFormat(name: String?, nameNumber: Int?): String {
        name?.let {
            nameNumber?.let {
                return name.replace(nameNumber.toString(), NAME_SEPARATOR)
            }
            return name
        }
        return ""
    }
}