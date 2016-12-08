package a450team3.tacoma.uw.edu.sylph.player;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;

import a450team3.tacoma.uw.edu.sylph.R;


public class ConvertActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        WebView convertWebView =(WebView)findViewById(R.id.convert_webview);
        convertWebView.getSettings().setJavaScriptEnabled(true);
        convertWebView.loadUrl("https://www.youtube2mp3.cc");

        convertWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        convertWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                String mp3Name = "Sylph_"+contentDisposition.substring(22,contentDisposition.length()-1);
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                String name = Environment.getExternalStorageDirectory().getAbsolutePath();
                File direct = new File(name + "/Sylph/");
                if (!direct.exists()) {
                    direct.mkdirs();
                }
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                request.allowScanningByMediaScanner();
                //request.setAllowedOverRoaming(false).setTitle("Demo");
                request.setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                request.setDestinationInExternalPublicDir("/Sylph/",mp3Name); //set title
                dm.enqueue(request);
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //This is important!
                intent.addCategory(Intent.CATEGORY_OPENABLE); //CATEGORY.OPENABLE
                intent.setType("*/*");//any application,any extension
                Toast.makeText(getApplicationContext(), "Downloading File \"" + mp3Name + "\"",  //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
