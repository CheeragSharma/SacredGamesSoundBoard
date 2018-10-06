package com.example.cheeshar.sacredgamessoundboard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScrollingActivity extends AppCompatActivity {

    private MediaPlayer mp;
    String buttonName;
    int sendMusicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        //long press listener
        for(int i = 1 ; i <= 2 ; i++)
        {
            buttonName = "sound" + i;
            int resId = getResources().getIdentifier(buttonName,"id" , getPackageName());
            Button buttonName = findViewById(resId);
            buttonName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopup(v);
                    return true;
                }
            });
        }

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.show();
        String tag = v.getTag().toString();
        sendMusicId = getResources().getIdentifier(tag,"raw",getPackageName());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                String mediaPath = "file://" + copyFileToExternalStorage(sendMusicId, "temp.mp3");
                Uri uri = Uri.parse(mediaPath);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("audio/mp3");
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(shareIntent, "Sending sound"));
                return true;
            }

        });
    }
    @Override
    protected void onPause()
    {
        stopPlaying();
        super.onPause();
    }


    @Override
    protected void onPause()
    {
        stopPlaying();
        super.onPause();
    }


    public void musicOnClick(View view)
    {
        stopPlaying();
        String tag = view.getTag().toString();
        int musicId = getResources().getIdentifier(tag,"raw",getPackageName());
        mp = MediaPlayer.create(this, musicId);
        mp.start();
    }

    protected void stopPlaying(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private String copyFileToExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" + resourceName;
        try{
            InputStream in = getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pathSDCard;
    }


}
