package com.example.cheeshar.sacredgamessoundboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScrollingActivity extends AppCompatActivity {

    private MediaPlayer mp;
    String buttonName;
    int sendMusicId;
    private static final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //long press listener
        for(int i = 1 ; i <= 43 ; i++)
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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(ScrollingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
        }

    private void requestPermission() {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ScrollingActivity.this,
                            "Cool! Permission accepted!", Toast.LENGTH_LONG).show();
                    String mediaPath = copyFileToExternalStorage(sendMusicId, "sound.mp3");
                    Uri uri = Uri.parse(mediaPath);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("audio/mp3");
                    shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                    startActivity(Intent.createChooser(shareIntent, "Sending sound"));
                } else {
                    Toast.makeText(ScrollingActivity.this,
                            "Permission denied! You won't be able to share!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    public void showPopup(View v) {
        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        PopupMenu popup;
        popup = new PopupMenu(wrapper, v, Gravity.RIGHT);
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
                    if (checkPermission()) {
                        String mediaPath = copyFileToExternalStorage(sendMusicId, "sound.mp3");
                        Uri uri = Uri.parse(mediaPath);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("audio/mp3");
                        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                        startActivity(Intent.createChooser(shareIntent, "Sending sound"));
                        return true;
                    } else {
                        requestPermission();
                    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_review:
                //redirect to play store Url
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com"));
                startActivity(intent);
                return true;
//          // case R.id.action_search:
//                //Do Something with search
//            //    return true;
            case R.id.action_ShareApp:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi! Install the Sacred Games Sound Board Apllication From Given Link. Link: ");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
