package pe.edu.cibertec.listarecordaudio;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    //    private ArrayList<Recording> recordingArrayList;
    List<Audio> audioArrayList = new ArrayList<>();
    private Context context;
    private MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private int last_index = -1;
    Audio audio;

    public AudioAdapter(Context context, ArrayList<Audio> audioArrayList) {
        this.context = context;
        this.audioArrayList = audioArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.prototype_audio, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setUpData(holder, position);
    }

    @Override
    public int getItemCount() {
        return audioArrayList.size();
    }


    private void setUpData(ViewHolder holder, int position) {

        Audio audio = audioArrayList.get(position);
        holder.textViewName.setText(audio.getName());

        if (audio.isPlaying()) {
            holder.imageViewPlay.setImageResource(R.drawable.ic_pause);
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            holder.seekBar.setVisibility(View.VISIBLE);
            holder.seekUpdation(holder);
        } else {
            holder.imageViewPlay.setImageResource(R.drawable.ic_play);
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            holder.seekBar.setVisibility(View.GONE);
        }


        holder.manageSeekBar(holder);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewPlay;
        SeekBar seekBar;
        TextView textViewName;
        private String audioUri;
        private int lastProgress = 0;
        private Handler mHandler = new Handler();
        ViewHolder holder;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewPlay = itemView.findViewById(R.id.imageViewPlay);
            seekBar = itemView.findViewById(R.id.seekBar);
            textViewName = itemView.findViewById(R.id.textViewRecordingname);

            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Audio audio = audioArrayList.get(position);
                    audioUri = audio.getUri();

                    if (isPlaying) {
                        stopPlaying();
                        if (position == last_index) {
                            audio.setPlaying(false);
                            stopPlaying();
                            notifyItemChanged(position);
                        } else {
                            markAllPaused();
                            audio.setPlaying(true);
                            notifyItemChanged(position);
                            startPlaying(audio, position);
                            last_index = position;
                        }

                    } else {
                        startPlaying(audio, position);
                        audio.setPlaying(true);
                        seekBar.setMax(mPlayer.getDuration());
                        Log.d("isPlayin", "False");
                        notifyItemChanged(position);
                        last_index = position;
                    }

                }

            });
        }

        public void manageSeekBar(ViewHolder holder) {
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (mPlayer != null && fromUser) {
                        mPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        private void markAllPaused() {
            for (int i = 0; i < audioArrayList.size(); i++) {
                audioArrayList.get(i).setPlaying(false);
                audioArrayList.set(i, audioArrayList.get(i));
            }
            notifyDataSetChanged();
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                seekUpdation(holder);
            }
        };

        private void seekUpdation(ViewHolder holder) {
            this.holder = holder;
            if (mPlayer != null) {
                int mCurrentPosition = mPlayer.getCurrentPosition();
                holder.seekBar.setMax(mPlayer.getDuration());
                holder.seekBar.setProgress(mCurrentPosition);
                lastProgress = mCurrentPosition;
            }
            mHandler.postDelayed(runnable, 100);
        }

        private void stopPlaying() {
            try {
                mPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPlayer = null;
            isPlaying = false;
        }

        private void startPlaying(final Audio audio, final int position) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(audioUri);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare() failed");
            }
            //showing the pause button
            seekBar.setMax(mPlayer.getDuration());
            isPlaying = true;

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audio.setPlaying(false);
                    notifyItemChanged(position);
                }
            });
        }

//


    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("ada");
        menu.add(1, 1, 1, "Delete");
//        menu.add(1, v.getId(), 1, "Delete");

    }


    public void remove(int position){
        audioArrayList.remove(position);
        notifyDataSetChanged();
    }

}