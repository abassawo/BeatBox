package abassawo.c4q.nyc.beatbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by c4q-Abass on 10/21/15.
 */
public class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);
        if(fragment == null){
           fragment = BeatBoxFragment.newInstance();
            fm.beginTransaction().add(R.id.main_container, fragment).commit();
        }

    }
}
