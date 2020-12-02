package com.example.fetchrewards.network

import com.example.fetchrewards.NAME_SEPARATOR
import com.example.fetchrewards.database.Item
import java.lang.StringBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemMapper @Inject constructor() {

    fun map(remoteItem: RemoteItem): Item {
        val namePrefix = getNamePrefix(remoteItem.name)
        return Item(
            id = remoteItem.id ?: 0,
            listId = remoteItem.listId ?: 0,
            namePrefix = namePrefix,
            nameNumber = getNameNumber(remoteItem.name, namePrefix.length - 1)
        )
    }

    private fun getNamePrefix(name: String?): String {
        name?.forEachIndexed { index, character ->
            if (character.isDigit()) {
                return name.substring(0, index) + NAME_SEPARATOR
            }
        }
        return ""
    }

    private fun getNameNumber(name: String?, start: Int): Int {
        name?.let {
            val builder = StringBuilder()
            for (index in start until name.length) {
                if (name[index].isDigit()) {
                    builder.append(name[index])
                } else {
                    break
                }
            }
            return Integer.parseInt(builder.toString())
        }
        return 0
    }
}