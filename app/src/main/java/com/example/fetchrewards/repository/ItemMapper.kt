package com.example.fetchrewards.repository

import androidx.annotation.VisibleForTesting
import com.example.fetchrewards.NAME_SEPARATOR
import com.example.fetchrewards.database.RoomItem
import javax.inject.Inject

class ItemMapper @Inject constructor() {

    fun map(roomItem: RoomItem): Item {
        return Item (
            id = roomItem.id,
            listId = roomItem.listId,
            name = formatName(roomItem.nameFormat, roomItem.nameNumber)
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun formatName(nameFormat: String, nameNumber: Int?): String {
        if (nameFormat.contains(NAME_SEPARATOR)) {
            return nameFormat.replace(NAME_SEPARATOR, nameNumber.toString())
        }
        return nameFormat
    }
}