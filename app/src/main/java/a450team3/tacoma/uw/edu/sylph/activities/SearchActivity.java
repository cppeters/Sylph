package a450team3.tacoma.uw.edu.sylph.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import a450team3.tacoma.uw.edu.sylph.NavActivity;
import a450team3.tacoma.uw.edu.sylph.R;

public class SearchActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);
        Uri webpage = Uri.parse("http://m.youtube.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);

        Uri data = webIntent.getData();

        System.out.println("Data: " + data.toString());

    }


}
