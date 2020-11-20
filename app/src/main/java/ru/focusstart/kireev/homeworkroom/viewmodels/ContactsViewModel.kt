package ru.focusstart.kireev.homeworkroom.viewmodels

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.focusstart.kireev.homeworkroom.database.Contact
import ru.focusstart.kireev.homeworkroom.database.MainDatabase

class ContactsViewModel(app: Application) : ViewModel() {
    private val db = MainDatabase.getInstance(app)

    val allContacts = db.contactsDao().getAllContacts()

    private fun insertAllContacts(contacts: List<Contact>) {
        db.contactsDao().insertContacts(contacts)
    }

    fun readContacts(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            db.contactsDao().deleteAllContacts()
            val contactsList = mutableListOf<Contact>()
            val contentUri = ContactsContract.Contacts.CONTENT_URI
            val id = ContactsContract.Contacts._ID
            val displayName = ContactsContract.Contacts.DISPLAY_NAME
            val hasPhoneNumber = ContactsContract.Contacts.HAS_PHONE_NUMBER
            val phoneContentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val phoneContactId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            val number = ContactsContract.CommonDataKinds.Phone.NUMBER


            val resolver = context.contentResolver
            val cursor = resolver?.query(contentUri, null, null, null, null)

            cursor?.let { notNullCursor ->
                if (notNullCursor.count > 0) {
                    while (notNullCursor.moveToNext()) {
                        val contactId =
                            notNullCursor.getString(notNullCursor.getColumnIndex(id))
                        val name =
                            notNullCursor.getString(notNullCursor.getColumnIndex(displayName))
                        val hadPhoneNumber =
                            notNullCursor.getString(notNullCursor.getColumnIndex(hasPhoneNumber))
                                .toInt()

                        if (hadPhoneNumber > 0) {
                            val listOfPhones = mutableListOf<String>()
                            val phoneCursor = resolver.query(
                                phoneContentUri,
                                null,
                                "$phoneContactId = ?",
                                arrayOf(contactId),
                                null
                            )

                            phoneCursor?.let { notNullPhoneCursor ->
                                while (notNullPhoneCursor.moveToNext()) {
                                    val phoneNumber = notNullPhoneCursor.getString(
                                        notNullPhoneCursor.getColumnIndex(number)
                                    )
                                    listOfPhones.add(phoneNumber)
                                }
                            }
                            phoneCursor?.close()
                            contactsList.add(Contact(name = name, phones = listOfPhones))
                        }
                    }
                }
            }
            cursor?.close()
            insertAllContacts(contactsList)
        }
    }
}