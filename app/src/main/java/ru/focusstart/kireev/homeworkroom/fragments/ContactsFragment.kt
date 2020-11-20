package ru.focusstart.kireev.homeworkroom.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import ru.focusstart.kireev.homeworkroom.R
import ru.focusstart.kireev.homeworkroom.adapters.ContactAdapter
import ru.focusstart.kireev.homeworkroom.viewmodels.ContactsViewModel
import ru.focusstart.kireev.homeworkroom.viewmodels.ContactsViewModelFactory

class ContactsFragment : Fragment(R.layout.fragment_contacts) {
    private val viewModel by lazy {
        ContactsViewModelFactory(requireActivity().application).create(ContactsViewModel::class.java)
    }

    companion object {
        fun newInstance() = ContactsFragment()
        private const val REQUEST_PERMISSION_CODE = 123
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactAdapter()
        view.recyclerViewContacts.adapter = adapter
        viewModel.allContacts.observe(viewLifecycleOwner) {
            adapter.contacts = it
            if (it.isNotEmpty()) {
                view.progressBar.visibility = View.INVISIBLE
            }
        }

        view.buttonLoadContacts.setOnClickListener { downloadData() }
    }

    private fun downloadData() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) -> {
                progressBar.visibility = View.VISIBLE
                viewModel.readContacts(requireContext())
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    progressBar.visibility = View.VISIBLE
                    viewModel.readContacts(requireContext())
                }
            }
        }
    }
}