package ru.focusstart.kireev.homeworkroom.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var phones: List<String>
)