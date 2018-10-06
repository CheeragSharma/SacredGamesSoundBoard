package com.example.cheeshar.sacredgamessoundboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScrollingActivity extends AppCompatActivity {

    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        //long press listener
        Button b1 = findViewById(R.id.sound1);

        b1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopup(v);
                return true;
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.share:
                        //shareclip();
                }
                return true;
            }

        });
    }

//    public void shareclip()
//    {
//
//    }


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
