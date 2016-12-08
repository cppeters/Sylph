package a450team3.tacoma.uw.edu.sylph.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import a450team3.tacoma.uw.edu.sylph.R;
import a450team3.tacoma.uw.edu.sylph.authenticate.LoginActivity;
import a450team3.tacoma.uw.edu.sylph.favorites.FavoriteActivity;
import a450team3.tacoma.uw.edu.sylph.player.SelectionActivity;

public class HomeActivity extends NavActivity {

    GoogleSignInAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);

        Bundle bundle = getIntent().getExtras();
        mAccount = (GoogleSignInAccount) bundle.get(LoginActivity.ACCOUNT_CODE);
    }

    public void youtubeClick(View v) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void favClick(View v) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        if (mAccount != null) {
            intent.putExtra(LoginActivity.ACCOUNT_CODE, LoginActivity.GOOGLE_ACCOUNT);
        }
        startActivity(intent);
    }

    public void searchClick(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
