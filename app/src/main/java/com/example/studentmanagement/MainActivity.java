package com.example.studentmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editTextId,editSurname,editMarks;
    Button btnAddData;
    Button btnViewAll;
    Button btnViewUpdate;
    Button btnDelete;
    ListView myListView;
    SimpleCursorAdapter mSCA;
    Cursor mCsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) this.findViewById(R.id.lv001)
        myDb = new DatabaseHelper(this);

        editTextId = (EditText)findViewById(R.id.editTextId);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnViewUpdate = (Button) findViewById( R.id.button_update);
        btnDelete = (Button) findViewById( R.id.button_delete);
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();



    }

    public void UpdateData () {
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(),editName.getText().toString(),
                                editSurname.getText().toString(),editMarks.getText().toString());
                        if(isUpdated == true)
                            Toast.makeText(MainActivity.this, "Data is updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is not updated", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString() );
                        if(isInserted ==true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data is not Inserted", Toast.LENGTH_LONG).show();



                    }
                }
        );
    }
    public void DeleteData() {

        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer delectedRows = myDb.deleteData(editTextId.getText().toString());
                        if (delectedRows > 0)
                            Toast.makeText(MainActivity.this, "Data has been deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data has not been deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    public void ViewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor results = myDb.getAllData();
                        if(results.getCount() == 0) {
                            showMessage("error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (results.moveToNext()) {
                            buffer.append("id:" + results.getString(0) +"\n");
                            buffer.append("Name:" + results.getString(1) +"\n");
                            buffer.append("Surname:" + results.getString(2) +"\n");
                            buffer.append("Marks:" + results.getString(3) +"\n\n");



                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}