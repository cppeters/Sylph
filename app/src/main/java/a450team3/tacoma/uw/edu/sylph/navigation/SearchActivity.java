package a450team3.tacoma.uw.edu.sylph.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
