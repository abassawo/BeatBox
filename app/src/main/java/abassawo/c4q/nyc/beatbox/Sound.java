package abassawo.c4q.nyc.beatbox;

import java.util.List;

/**
 * Created by c4q-Abass on 10/23/15.
 */
public class Sound {
    private String mAssetPath;
   // private List<Sound> mSounds;

    public Sound(String assetPath){
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public Integer getSoundId() {

        return mSoundId;
    }

    public void setSoundId(Integer soundId){
        this.mSoundId = soundId;
    }

    public String getName() {

        return mName;
    }

    private String mName;
    private Integer mSoundId; //using Integer to make it possible to represent null values.


}
