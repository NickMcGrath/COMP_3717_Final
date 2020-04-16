package ca.bcit.android_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.addToListBtn)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                addMovie();
            }
        });
    }

    /**
     * Checks if movie input data is valid.
     *
     * @return Boolean
     */
    private boolean validateMovieData(Map<String, Object> data) {
        for (Object item : data.values()) {
            if (item != null)
                if (item instanceof String && !item.equals(""))
                    continue;
//                else if ()  if other object types check them here, currently all are strings
            Toast.makeText(getApplicationContext(), "Check your input!", Toast.LENGTH_SHORT).show();
            return false; // if it got to here there was an error! so exit method
        }

        //the collection is not stored in the movie data as it is the title for the data
        String collection = ((EditText) findViewById(R.id.userIdInput)).getText().toString();
        if (collection == null || collection.equals("")) {
            Toast.makeText(getApplicationContext(), "Check your input!", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    /**
     * Checks if the input data input is valid, Connects to firebase and uploads the movie data.
     */
    private void addMovie() {
        // Create a new user with a first and last name
        Map<String, Object> movie = new HashMap<>();
        movie.put("title", ((EditText) findViewById(R.id.movieNameInput)).getText().toString());
        movie.put("description", ((EditText) findViewById(R.id.descriptionInput)).getText().toString());
        movie.put("IMDB_link", ((EditText) findViewById(R.id.linkInput)).getText().toString());
        if (!validateMovieData(movie))
            return;

        String collection = ((EditText) findViewById(R.id.userIdInput)).getText().toString();
        String document = ((EditText) findViewById(R.id.movieNameInput)).getText().toString();

        db.collection(collection).document(document).set(movie).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Successfully added to database!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
