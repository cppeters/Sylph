package a450team3.tacoma.uw.edu.sylph.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import a450team3.tacoma.uw.edu.sylph.R;

/**
 * Search Activity Class
 * created by cppeters
 */
public class SearchActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);

        Uri webpage = Uri.parse("http://m.youtube.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);

        // Uri info to pass back, needs listener?
        Uri data = webIntent.getData();
    }


}
