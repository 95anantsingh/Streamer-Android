package inc.humanoid.streamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class FullscreenActivity extends AppCompatActivity {

    private WebView webview;
    private SharedPreferences settings;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContentView = findViewById(R.id.relative_layout);
        hideUI();

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        webview = findViewById(R.id.web_view);
        loadVideo();
    }

//-----------------------------------------Control Buttons---------------------------------------//

    public void tuneButton(View v) {
        Intent intent = new Intent(FullscreenActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    public void refreshButton(View v) {
        reloadVideo();
    }


//------------------------------------------Video Handler-----------------------------------------//

    private void loadVideo() {
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.loadUrl("http://"
                + settings.getString("ip_address", "")
                + ":"
                + settings.getString("video_port", "")
                + "/"
                + settings.getString("video_address", ""));
    }
    public void reloadVideo() {

        webview.reload();
    }

//------------------------------------------Fullscreen--------------------------------------------//

    private void hideUI() {
    mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
}

//----------------------------------------Override methods----------------------------------------//

    @Override
    protected void onStop() {
        super.onStop();
        webview.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.reload();
        hideUI();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadVideo();
    }
}