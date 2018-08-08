package alankrita.in.voicesamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button speakers, items, record, settings;
        TextView speakername;

        speakername = (TextView) findViewById(R.id.speakername);
        speakers = (Button) findViewById(R.id.button);
        items = (Button) findViewById(R.id.button2);
        record = (Button) findViewById(R.id.button3);
        settings = (Button) findViewById(R.id.button4);

        speakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SpeakersActivity.class);
                startActivity(in);
            }
        });

        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(MainActivity.this, ItemsActivity.class);
//                startActivity(in);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(in);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(in);
            }
        });

    }
}
