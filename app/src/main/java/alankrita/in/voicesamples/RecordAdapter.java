package alankrita.in.voicesamples;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>
        implements Filterable {

    private Context context;
    public List<String> audioList;
    public List<String> audioListFiltered;
    private RecordAdapterListener recordAdapterListener;
    private MediaPlayer mPlayer = null;
    Handler handler;
    private int mediaPos, mediaMax;


    public RecordAdapter(Context context,
                              List<String> audioList,
                              RecordAdapterListener recordAdapterListener) {
        this.audioList = audioList;
        this.context = context;
        this.audioListFiltered = audioList;
        this.recordAdapterListener = recordAdapterListener;
    }

    private void onPlay(boolean start, String mFileName) {
        if (start) {
            startPlaying(mFileName);
        } else {
            stopPlaying();
        }
    }

    private void startPlaying(String mFileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

//    private Runnable moveSeekBarThread = new Runnable() {
//
//        public void run() {
//            if(mPlayer.isPlaying()){
//
//                int mediaPos_new = mPlayer.getCurrentPosition();
//                int mediaMax_new = mPlayer.getDuration();
//                seekBar.setMax(mediaMax_new);
//                seekBar.setProgress(mediaPos_new);
//
//                handler.postDelayed(this, 100); //Looping the thread after 0.1 second
//                // seconds
//            }
//        }
//    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public Button playbutton;
        public SeekBar seekbar;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            playbutton = (Button) mView.findViewById(R.id.playbutton);
            seekbar =(SeekBar) mView.findViewById(R.id.seekbar);
            handler = new Handler() {
                @Override
                public void publish(LogRecord record) {

                }

                @Override
                public void flush() {

                }

                @Override
                public void close() throws SecurityException {

                }
            };

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recordAdapterListener.onAudioSelected(audioListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_audio, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(true, audioList.get(position));

//                Handler mHandler = new Handler();
////Make sure you update Seekbar on UI thread
//                RecordAdapter.this.runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if(mPlayer != null){
//                            int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
//                            mSeekBar.setProgress(mCurrentPosition);
//                        }
//                        mHandler.postDelayed(this, 1000);
//                    }
//                });
//                mediaPos = mPlayer.getCurrentPosition();
//                mediaMax = mPlayer.getDuration();
//
//                holder.seekbar.setMax(mediaMax); // Set the Maximum range of the
//                holder.seekbar.setProgress(mediaPos);// set current progress to song's
//
//                handler.removeCallbacks(moveSeekBarThread);
//                handler.postDelayed(moveSeekBarThread, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    audioListFiltered = audioList;
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String row : audioList) {
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    audioListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = audioListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                audioListFiltered = (ArrayList<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface RecordAdapterListener{
        void onAudioSelected(String str);
    }
}
