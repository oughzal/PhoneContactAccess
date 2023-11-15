package com.omarcomputer.phonecontactaccess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.omarcomputer.phonecontactaccess.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Contact> contactList = new ArrayList<>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getContacts();
        showContact();

        binding.btnPrevious.setOnClickListener(v -> {
            if (index > 0 && contactList.size() > 0) {
                index--;
                showContact();
            }
        });
        binding.btnNext.setOnClickListener(v -> {
            if (index < contactList.size() -1) {
                index++;
                showContact();
            }
        });
    }

    private void showContact() {
        if(contactList.size()==0) return;
        Contact contact = contactList.get(index);
        binding.txtName.setText(contact.name);
        binding.txtPhoneNumber.setText(contact.phoneNumber);
        binding.txtCurrentContact.setText(""+ (index+1) + "/"+ contactList.size());
    }

    private void getContacts() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},100);
            return;
        }
        contactList.clear();
        ContentResolver contentResolver = this.getContentResolver(); // ContentResolver : obtenir le contenu du téléphone
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, // type du contenu
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        if(cursor !=null) {
            try {
                while(cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact contact = new Contact(name, phoneNumber);
                    contactList.add(contact);
                }
            } catch (Exception e) {

            } finally {
                cursor.close();
            }
        }
    }

}