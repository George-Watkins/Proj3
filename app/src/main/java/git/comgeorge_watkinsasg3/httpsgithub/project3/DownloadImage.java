package git.comgeorge_watkinsasg3.httpsgithub.project3;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            if(httpconn.getResponseCode() != 200){
                throw new Exception("Failed to connect");
            }

            InputStream is = httpconn.getInputStream();
            return BitmapFactory.decodeStream(is);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
