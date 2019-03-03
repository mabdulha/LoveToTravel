package ie.com.lovetotravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import ie.com.lovetotravel.R;
import ie.com.lovetotravel.authentication.Login;


public class Base extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabaseRef;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intentHome = new Intent(this, Home.class);
                this.startActivity(intentHome);
                return true;
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intentLogout = new Intent(this, Login.class);
                this.startActivity(intentLogout);
                return true;
            case R.id.menu_search:
                Intent intentSearch = new Intent(this, Search.class);
                this.startActivity(intentSearch);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //https://stackoverflow.com/questions/18977187/how-to-hide-soft-keyboard-when-activity-starts
    //This is the reference for this code to hide the keyboard

   public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
