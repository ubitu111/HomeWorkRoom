package ru.focusstart.kireev.homeworkroom.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.focusstart.kireev.homeworkroom.R
import ru.focusstart.kireev.homeworkroom.fragments.ContactsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            addFragment()
        }
    }

    private fun addFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, ContactsFragment.newInstance())
        transaction.commit()
    }
}