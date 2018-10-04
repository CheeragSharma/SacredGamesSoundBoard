package com.example.cheeshar.sacredgamessoundboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScrollingActivity extends AppCompatActivity {

    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
    }



    public void musicOnClick(View view)
    {
        stopPlaying();
        switch (view.getId())
        {
            case R.id.sound1:
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
                break;
            case R.id.sound2:
                mp = MediaPlayer.create(this, R.raw.sound2);
                mp.start();
                break;
        }
    }

    protected void stopPlaying(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }


}
