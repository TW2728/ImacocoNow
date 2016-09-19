package tuyosi_wada_music.outlook.jp.imacoconya_n;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class TweetActivity extends AppCompatActivity implements  View.OnClickListener{

    private EditText tweet;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String txt = new String();
        txt += sp.getString("tweet_header",null);

        if(sp.getBoolean("tag",true)){
            txt += "#imacoconow ";
        }
        txt += sp.getString("tweet_footer",null);
        tweet.setText(txt);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tweet_button:
                
        }
    }
}
