package com.qtalk.sample.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.qtalk.sample.R
import com.qtalk.sample.adapters.ContactsAdapter
import com.qtalk.sample.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var mNameList: ArrayList<String> = ArrayList()
    private lateinit var binding: FragmentContactsBinding

    companion object {
        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentContactsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //programmatically alter the `width` and `height` of the PopupTextView of FastScroller
        with(binding.fastScrollerContacts) {
            popupTextView.layoutParams.width =
                activity?.resources?.getDimension(R.dimen.contacts_popup_size)?.toInt() ?: 0
            popupTextView.layoutParams.height =
                activity?.resources?.getDimension(R.dimen.contacts_popup_size)?.toInt() ?: 0
            popupTextView.requestLayout()
        }
        with(binding.contactsRecyclerView)
        {
            adapter = ContactsAdapter(activity, mNameList)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            //checking for permissions to fetch contacts
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity?.checkSelfPermission(
                    android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS
                )
            } else {
                if (mNameList.size == 0)
                    retrieveContacts() else {}
            }
        }
    }

    @SuppressLint("Range")
    private fun retrieveContacts() {
        activity
            ?.contentResolver
            ?.let { contentResolver ->
                contentResolver
                    .query(
                        ContactsContract.Contacts.CONTENT_URI,
                        arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
                        null,
                        null,
                        "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
                    )?.use {
                        while (it.moveToNext()) {
                            mNameList.add(it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                        }
                    }
            }
        binding.contactsRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            retrieveContacts()
        else
            Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show()
    }
}
