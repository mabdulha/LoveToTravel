package ie.com.lovetotravel.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.fragments.DatePickerFragment;
import ie.com.lovetotravel.models.Journal;

public class Add extends Base implements DatePickerDialog.OnDateSetListener {

    EditText etTitle, etEntry;
    TextView tvDate;
    Button btnDate, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("journals").child(user.getUid());

        etTitle = (EditText) findViewById(R.id.et_add_title);
        etEntry = (EditText) findViewById(R.id.et_add_entry);
        tvDate = (TextView) findViewById(R.id.tv_add_date_show);
        btnDate = (Button) findViewById(R.id.btn_add_date_picker);
        btnAdd = (Button) findViewById(R.id.btn_add_journal);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournal();
            }
        });
    }

    public void addJournal () {

        String title = etTitle.getText().toString().trim();
        String entry = etEntry.getText().toString().trim();
        String date = tvDate.getText().toString().trim();

        if(title.length() > 0 && entry.length() > 0 && date.length() > 5) {

            Journal journal = new Journal(title, entry, date, false);
            FirebaseUser user = mAuth.getCurrentUser();
            String journalId = mDatabaseRef.push().getKey();
            mDatabaseRef.child(journalId).setValue(journal);

            Intent intentHome = new Intent(this, Home.class);
            this.startActivity(intentHome);

            Toast.makeText(this, "Journal has been saved.", Toast.LENGTH_SHORT).show();
        }

        else {

            Toast.makeText(this, "Please enter values into all fields and choose a date", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tvDate.setText(currentDate);
    }
}