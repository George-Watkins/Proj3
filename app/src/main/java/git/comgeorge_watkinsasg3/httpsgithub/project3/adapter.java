package git.comgeorge_watkinsasg3.httpsgithub.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import git.comgeorge_watkinsasg3.httpsgithub.project3.DownloadImageTask;
import git.comgeorge_watkinsasg3.httpsgithub.project3.R;
import git.comgeorge_watkinsasg3.httpsgithub.project3.proj3;

/**
 * Created by Perkins on 10/28/2015.
 */
public class adapter extends BaseAdapter {

    //for layouts
    LayoutInflater myInflater;

    //
    public static class ViewHolder {
        ImageView img1;
        TextView tv1;
        String url;
    }

    //genrerate data we use
    private String[] values;

    private void generateData() {
//        values = new String[]{"http://cnu.edu/pcs/pcsebloglogo.png"
//        };
        values = new String[]{"http://cnu.edu/pcs/pcsebloglogo.png",
                "http://cnu.edu/images/footer/facebook.png",
                "http://cnu.edu/images/footer/wordpress.png",
                "http://cnu.edu/images/footer/twitter.png",
                "http://cnu.edu/images/footer/youtube.png",
                "http://cnu.edu/images/footer/instagram.png"
        };
    }

    private final proj3 mainActivity;

    public adapter(proj3 activity) {
        this.mainActivity = activity;
        myInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        generateData();
    }

    @Override
    public int getCount () {
        return values.length;
    }

    @Override
    public Object getItem ( int position){
        return null;
    }

    @Override
    public long getItemId ( int position){
        return 0;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.activity_proj3, null);

            holder = new ViewHolder();
            holder.img1 = (ImageView) convertView.findViewById(R.id.backgroundImage);
            holder.tv1 = (TextView) convertView.findViewById(R.id.editText);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        //set the text
        holder.tv1.setText(values[position]);
        holder.url = values[position];

        //start a thread to download image
        new DownloadImageTask(holder).execute(holder.url);

        return convertView;
    }
}
