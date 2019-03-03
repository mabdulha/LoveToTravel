package ie.com.lovetotravel.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.fragments.DatePickerFragment;
import ie.com.lovetotravel.models.Journal;

public class Update extends Base implements DatePickerDialog.OnDateSetListener {

    EditText updateTitle, updateEntry;
    TextView tvUpdateDate;
    Button btnUpdate, btnDate;

    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Update");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("journals").child(user.getUid());

        mKey = getIntent().getExtras().getString("journal_id");

        updateTitle = (EditText) findViewById(R.id.et_update_title);
        updateEntry = (EditText) findViewById(R.id.et_update_entry);
        tvUpdateDate = (TextView) findViewById(R.id.tv_update_date_show);
        btnDate = (Button) findViewById(R.id.btn_update_date_picker);
        btnUpdate = (Button) findViewById(R.id.btn_update_journal);

        mDatabaseRef.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String insert_title = (String) dataSnapshot.child("title").getValue();
                String insert_entry = (String) dataSnapshot.child("entry").getValue();
                String insert_date = (String) dataSnapshot.child("date").getValue();

                updateTitle.setText(insert_title);
                updateEntry.setText(insert_entry);
                tvUpdateDate.setText(insert_date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updateTitle.length() > 0 && updateEntry.length() > 0 && tvUpdateDate.length() > 5) {

                    Journal journal = new Journal(updateTitle.getText().toString().trim(),
                            updateEntry.getText().toString().trim(),
                            tvUpdateDate.getText().toString().trim(),
                            false);
                    mDatabaseRef.child(mKey).setValue(journal);

                    Intent intentHome = new Intent(Update.this, Home.class);
                    startActivity(intentHome);

                    Toast.makeText(getApplicationContext(), "Journal has been saved.", Toast.LENGTH_SHORT).show();
                }

                else {

                    Toast.makeText(getApplicationContext(), "Please enter values into all fields and choose a date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tvUpdateDate.setText(currentDate);
    }
}
