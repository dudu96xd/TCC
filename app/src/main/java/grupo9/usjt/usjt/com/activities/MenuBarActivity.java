package grupo9.usjt.usjt.com.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

public class MenuBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // more code as before
    }

    //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here
            return(true);
        case R.id.about:
            //add the function to perform here
            return(true);
        case R.id.exit:
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}
