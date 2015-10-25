package abassawo.c4q.nyc.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by c4q-Abass on 10/21/15.
 */
public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
        try {
            mBeatBox.playRandom();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_beat_box, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        return view;
    }

    private class SoundButtonViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        private View view;


        private Button mButton;
        private Sound mSound;

        public SoundButtonViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_item_sound, container, false));
            view = inflater.inflate(R.layout.list_item_sound, container, false);
            mButton = (Button) itemView.findViewById(R.id.list_item_sound_button); //Note itemview;
            mButton.setOnClickListener(this);
        }

        public void bindSound(Sound sound){
            mSound = sound;
            mButton.setText(mSound.getName());
        }


        @Override
        public void onClick(View v) {
            mBeatBox.play(mSound);
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundButtonViewHolder>  implements View.OnClickListener{
        private List<Sound> mSounds;

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mSounds.get(0).getName(), Toast.LENGTH_LONG);
        }

        public SoundAdapter(List<Sound> soundList){
            mSounds = soundList;
        }

        @Override
        public SoundButtonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
           LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundButtonViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(SoundButtonViewHolder soundButtonViewHolder, int i) {
            Sound sound = mSounds.get(i);
            soundButtonViewHolder.bindSound(sound);

        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
