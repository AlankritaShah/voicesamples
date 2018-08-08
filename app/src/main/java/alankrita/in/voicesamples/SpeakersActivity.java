package alankrita.in.voicesamples;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpeakersActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ArrayList<String> usersList = new ArrayList<>();
    // FloatingActionButton  speaker_image_Button=findViewById(R.id.speaker_image_Button);;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.i(String.valueOf(databaseReference), "I am here");
        listView = findViewById(R.id.speakers_listview);
        Log.i(String.valueOf(databaseReference), "I am here now!!");

      //  TextView userslistname = (TextView) findViewById(R.id.users_list_name);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.username,usersList);
//        if(!usersList.isEmpty())
//        userslistname.setText(usersList.get(arrayAdapter.getCount()));
        listView.setAdapter(arrayAdapter);

        findViewById(R.id.speaker_image_Button).setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    Map userinfo = ((Map) i.getValue());
                    Map someinfo = (Map) userinfo.get("UserInfo");
                    String somename = someinfo.get("user_name").toString();
                    usersList.add(somename);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.username, usersList);

                    Log.i("namedata", somename);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.speaker_image_Button:
                startActivity(new Intent(SpeakersActivity.this, AddSpeakers.class));
                break;
        }
    }
}
