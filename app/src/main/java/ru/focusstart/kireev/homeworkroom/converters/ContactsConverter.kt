package ru.focusstart.kireev.homeworkroom.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.focusstart.kireev.homeworkroom.database.Contact

class ContactsConverter {
    @TypeConverter
    fun contactToString(contact: Contact): String = Gson().toJson(contact)

    @TypeConverter
    fun stringToContact(contactAsString: String): Contact =
        Gson().fromJson(contactAsString, Contact::class.java)

    @TypeConverter
    fun listStringsToString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun stringToListStrings(listAsString: String): List<String> =
        Gson().fromJson(listAsString, Array<String>::class.java).toList()
}