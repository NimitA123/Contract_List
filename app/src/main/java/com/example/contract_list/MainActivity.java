package com.example.contract_list;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ListView listView;
   private List<List<String>> data = new ArrayList<List<String>>();
   private Button button;
   private Button button1;
    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.getContract);
        button1 = findViewById(R.id.Contract);
        getList();

    }

    private void getList() {
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                startManagingCursor(cursor);
                String[]  form = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};
                int[] to = {android.R.id.text1, android.R.id.text2};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, cursor,form, to);
                 listView.setAdapter(simpleCursorAdapter);
                 listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                if(cursor.moveToFirst())
                {
                    ArrayList<String> alContacts = new ArrayList<String>();
                    do
                    {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                        if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                        {
                            Cursor pCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                            while (pCur.moveToNext())
                            {
                                List<String> contactNumber = Collections.singletonList(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                data.add(contactNumber);
                                break;
                            }
                            pCur.close();
                        }

                    } while (cursor.moveToNext()) ;
                }



            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService = Network.getInstance().create(ApiService.class);
                apiService.data(data);
                Toast.makeText(MainActivity.this, "Data Upload SuccessFully", Toast.LENGTH_LONG).show();
            }
        });
        ApiService apiService = Network.getInstance().create(ApiService.class);
        apiService.data(data);
    }

}