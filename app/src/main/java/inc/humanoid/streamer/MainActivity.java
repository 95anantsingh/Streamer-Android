package inc.humanoid.streamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        FloatingActionButton fab = findViewById(R.id.fab);
        final Intent intent =new Intent(this,FullscreenActivity.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        TextView abc = findViewById(R.id.text);
    }
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        AppBarHandler();
        return true;
    }

    private void AppBarHandler(){
        if(menu != null) menu.clear();
        if (!settings.getBoolean("hide_about_section", Boolean.parseBoolean(""))){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        else if(settings.getBoolean("hide_about_section", Boolean.parseBoolean(""))){
            getMenuInflater().inflate(R.menu.menu_main2, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent =new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.about) {
            Intent intent =new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (menu != null)
            AppBarHandler();
    }
}
