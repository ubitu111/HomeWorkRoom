package ru.focusstart.kireev.homeworkroom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<Contact>)

    @Query("SELECT * FROM contacts ORDER BY name")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("DELETE FROM contacts")
    fun deleteAllContacts()
}