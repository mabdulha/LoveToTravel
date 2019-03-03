package ie.com.lovetotravel.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.viewholder.JournalViewHolder;
import ie.com.lovetotravel.models.Journal;

public class Search extends Base {

    private EditText etSearch;
    private ImageButton btnSearch;
    private RecyclerView rvResult;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Search");

        etSearch = (EditText) findViewById(R.id.et_search_bar);
        btnSearch = (ImageButton) findViewById(R.id.imgbtn_search);
        rvResult = (RecyclerView) findViewById(R.id.rv_search_result);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("journals").child(currentUserId);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jSearch = etSearch.getText().toString().trim();
                journalSearch(jSearch);
                hideSoftKeyboard();
            }
        });
    }

    private void journalSearch(String jSearch) {

        Query journalSearch = mDatabaseRef.orderByChild("title").startAt(jSearch).endAt(jSearch + "\uf8ff");

        FirebaseRecyclerAdapter<Journal,JournalViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Journal, JournalViewHolder>(
                Journal.class,
                R.layout.journal_card_layout,
                JournalViewHolder.class,
                journalSearch
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
                        Intent intent = new Intent(Search.this, JournalView.class);
                        intent.putExtra("journal_id", ref_key);
                        startActivity(intent);
                    }
                });

                viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Search.this, Update.class);
                        intent.putExtra("journal_id" ,ref_key);
                        startActivity(intent);
                    }
                });

                viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog dialog = new AlertDialog.Builder(Search.this)
                                .setTitle("Delete Confirmation")
                                .setMessage("Are you sure you want to delete this journal?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDatabaseRef.child(ref_key).removeValue();
                                        Toast.makeText(Search.this, "Journal Deleted", Toast.LENGTH_SHORT).show();
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

        rvResult.setAdapter(firebaseRecyclerAdapter);
    }
}
