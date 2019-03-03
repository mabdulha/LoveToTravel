package ie.com.lovetotravel.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.viewholder.JournalViewHolder;
import ie.com.lovetotravel.models.Journal;

public class Home extends Base {

    RecyclerView journalView;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Journals");

        journalView = (RecyclerView) findViewById(R.id.rv_journal_list);
        journalView.setHasFixedSize(true);
        journalView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("journals").child(currentUserId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Home.this, Add.class));
            }
        });
    }


        @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Journal, JournalViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Journal, JournalViewHolder>(
                Journal.class,
                R.layout.journal_card_layout,
                JournalViewHolder.class,
                mDatabaseRef
        ) {
            @Override
            protected void populateViewHolder(final JournalViewHolder viewHolder, final Journal model, int position) {

                final String ref_key = getRef(position).getKey();


                viewHolder.setTitle(model.getTitle());
                viewHolder.setEntry(model.getEntry());
                viewHolder.setDate(model.getDate());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, JournalView.class);
                        intent.putExtra("journal_id", ref_key);
                        startActivity(intent);
                    }
                });

                viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Home.this, Update.class);
                        intent.putExtra("journal_id" ,ref_key);
                        startActivity(intent);
                    }
                });

                viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog dialog = new AlertDialog.Builder(Home.this)
                                .setTitle("Delete Confirmation")
                                .setMessage("Are you sure you want to delete this journal?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDatabaseRef.child(ref_key).removeValue();
                                        Toast.makeText(Home.this, "Journal Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                });
            }
        };

        journalView.setAdapter(firebaseRecyclerAdapter);
    }

    //https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity
    //Code is from stack overflow to prevent the user pressing back on the home activity to go to the login screen
    //Instead it will go back out the app
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}