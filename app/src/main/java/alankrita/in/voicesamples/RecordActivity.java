package alankrita.in.voicesamples;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity implements RecordAdapter.RecordAdapterListener{
    private Uri filePath;
    private StorageReference mstorage;
    public String userselected, itemselected;
    static int counter;
    public RecordAdapter recordAdapter;
    public List<String> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        userselected = "user1";
        itemselected = "item1";
        counter=1;

        mstorage = FirebaseStorage.getInstance().getReference();
        final Button listenbutton, savebutton, cancelbutton;
        TextView itemname;
        final RecyclerView recyclerViewList;

        audioList = new ArrayList<>();
        itemname = (TextView) findViewById(R.id.itemname);
        listenbutton = (Button) findViewById(R.id.button);
        savebutton = (Button) findViewById(R.id.save);
        cancelbutton = (Button) findViewById(R.id.cancel);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewList.setItemAnimator(new DefaultItemAnimator());


        mStartRecording = true;
        mStartPlaying = true;

        final RecordAdapter.RecordAdapterListener rlistener = this;
        recordAdapter = new RecordAdapter(getApplicationContext(), audioList, this);
        recyclerViewList.setAdapter(recordAdapter);

//        RecordButton = (Button) findViewById(R.id.btRecord);
//        RecordButton.setOnClickListener(this);
//        PlayButton = (Button) findViewById(R.id.btPlay);
//        PlayButton.setOnClickListener(this);

        listenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start listening
                onRecord(mStartRecording);
                if (mStartRecording) {
                    listenbutton.setText("Stop recording");
                } else {
                    listenbutton.setText("Start recording");
                    counter++;
                    audioList.add(mFileName);
                    recyclerViewList.setAdapter(new RecordAdapter(getApplicationContext(), audioList, rlistener));
                    uploadFile();
                }
                mStartRecording = !mStartRecording;
            }
        });

    }

    private static String mFileName = null;
  //  mFileName += "my_Record";

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    boolean mStartPlaying, mStartRecording;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }



    private void startRecording() {

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/"+userselected+itemselected+counter+".3gp";
        filePath = Uri.fromFile(new File(mFileName));

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }

        mRecorder.start();
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading");
//            progressDialog.show();


            StorageReference riversRef = mstorage.child("audio").child(userselected).child(itemselected+counter);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            //  progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            //  progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            //   progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
//        Intent intent = new Intent(RecordActivity.this, SearchCustomer.class);
//        // intent.putExtra("billname", "bill"+ldb.BILLNO);
//        startActivity(intent);
//        finish();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void demo() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "my_Record";
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
    @Override
    public void onAudioSelected(String filestr) {

        Log.d("string form", filestr);
    }


}
