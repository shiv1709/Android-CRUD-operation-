package com.example.dbsqliteex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     private DatabaseHelper myDb;
     private EditText editName,editSurname,editMarks,editId;
     private Button btnAddData,btnViewAllData,btnUpdateData,btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        editId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.addData_button);
        btnViewAllData = (Button)findViewById(R.id.viewAll_button);
        btnUpdateData = (Button)findViewById(R.id.update_button);
        btnDeleteData = (Button)findViewById(R.id.delete_button);
        insertToDb();
        getAllDataFromDb();
        updateDataInDb();
        deleteDataFromDb();
    }

    public void insertToDb(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        String id = editId.getText().toString();
                        String name = editName.getText().toString();
                        String surname = editSurname.getText().toString();
                        String marks = editMarks.getText().toString();
                      boolean isInserted =  myDb.insertData(id,name,surname,marks);
                      if(isInserted == true){
                          Toast.makeText(MainActivity.this,"Data successfully inserted into database"
                          ,Toast.LENGTH_LONG).show();
                      }
                      else{
                          Toast.makeText(MainActivity.this,"Data is unable to be inserted into database"
                                  ,Toast.LENGTH_LONG).show();
                      }

                    }
                }
        );

    }

   public  void getAllDataFromDb(){
        btnViewAllData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Cursor res = myDb.getAllData();
                        if(res.getCount()==0){
                            //show messages
                            showMessage("Error","Nothing found");
                            return;
                        }
                        else{
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()){
                                //column number starts from 0 index
                                buffer.append("ID : "+res.getString(0) +"\n" );
                                buffer.append("NAME : "+res.getString(1) +"\n" );
                                buffer.append("SURNAME : "+res.getString(2) +"\n" );
                                buffer.append("MARKS : "+res.getString(3) +"\n" );

                                showMessage("Data",buffer.toString());
                                //show all Data
                            }
                        }
                    }
                }
        );

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateDataInDb(){
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = editId.getText().toString();
                        String name = editName.getText().toString();
                        String surname = editSurname.getText().toString();
                        String marks = editMarks.getText().toString();
                        boolean isUpdated = myDb.updateData(id,name,surname,marks);

                        if(isUpdated==true){
                            Toast.makeText(MainActivity.this,"Data is successfully updated into database"
                                    ,Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data is unable to update into database"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void deleteDataFromDb(){
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String id  = editId.getText().toString();
                        Integer deletedRows = myDb.deleteData(id);
                        if(deletedRows>0){
                            //means at least one row is affected from delete operation
                            Toast.makeText(MainActivity.this,"Data deleted successfully"
                                    ,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"No corresponding data available to delete"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

}
