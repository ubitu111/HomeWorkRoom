package ru.focusstart.kireev.homeworkroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.focusstart.kireev.homeworkroom.converters.ContactsConverter

@Database(entities = [Contact::class], version = 1, exportSchema = false)
@TypeConverters(value = [ContactsConverter::class])
abstract class MainDatabase : RoomDatabase() {
    companion object {
        private var db: MainDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): MainDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    MainDatabase::class.java,
                    DB_NAME
                )
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun contactsDao(): ContactsDao
}