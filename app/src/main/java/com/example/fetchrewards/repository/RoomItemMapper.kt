package com.example.fetchrewards.repository

import com.example.fetchrewards.NAME_SEPARATOR
import com.example.fetchrewards.database.RoomItem
import javax.inject.Inject

class RoomItemMapper @Inject constructor() {

    fun map(roomItem: RoomItem): Item {
        return Item (
            id = roomItem.id,
            listId = roomItem.listId,
            name = formatName(roomItem.nameFormat, roomItem.nameNumber)
        )
    }

    private fun formatName(nameFormat: String, nameNumber: Int?): String {
        if (nameFormat.contains(NAME_SEPARATOR)) {
            return nameFormat.replace(NAME_SEPARATOR, nameNumber.toString())
        }
        return nameFormat
    }
}