package abassawo.c4q.nyc.beatbox;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by c4q-Abass on 10/21/15.
 */
public class BeatBox {

    private AssetManager mAssets;
    private List<Sound>mSounds = new ArrayList<>();
    private static final String TAG = "BeatBox";
    private static final int MAX_SOUNDS = 5;
    private SoundPool mSoundPool;

    private static final String SOUNDS_FOLDER = "sample_sounds";

    public BeatBox(Context ctx){
        mAssets = ctx.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void load(Sound sound)throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundID = mSoundPool.load(afd, 1);
        sound.setSoundId(soundID);
    }


    public  void loadSounds(){
        String[] soundNames;
        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.d("ABASS", "Found " + soundNames.length + " sounds"); //22 sounds. test
            for (String fileName : soundNames) {
                String assetPath = SOUNDS_FOLDER + "/" + fileName;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }

        } catch(IOException ioe){
            Log.d(TAG, "ABASS WTF", ioe);
           // ioe.printStackTrace();
        }

    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void playRandom() throws IOException {
       final int random = ThreadLocalRandom.current().nextInt(1, 9);
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               for (int i = 0; i < mSounds.size() ; i++) {
                    Sound sound = mSounds.get(i);
                   if(i % random == 0){
                       try {
                           load(sound);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       play(sound);
                   }
                   mSoundPool.autoResume();
                   mSoundPool.setLoop(sound.getSoundId(), random);

               }
           }
       };
        runnable.run();
        new Handler().postDelayed(runnable, 5);

    }

    public List<Sound> getSounds(){
        return mSounds;
    }

    public void release(){
        mSoundPool.release();
    }
}
