package ie.com.lovetotravel.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ie.com.lovetotravel.R;

public class JournalView extends Base {

    private String mKey = null;

    TextView vTitle, vEntry, vDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Journal");

        mKey = getIntent().getExtras().getString("journal_id");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("journals").child(user.getUid());

        vTitle = (TextView) findViewById(R.id.tv_view_Title);
        vEntry = (TextView) findViewById(R.id.tv_view_entry);
        vDate = (TextView) findViewById(R.id.tv_view_date);

        mDatabaseRef.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String jourTitle = (String) dataSnapshot.child("title").getValue();
                String jourEntry = (String) dataSnapshot.child("entry").getValue();
                String jourDate = (String) dataSnapshot.child("date").getValue();
                vTitle.setText(jourTitle);
                vEntry.setText(jourEntry);
                vDate.setText(jourDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
