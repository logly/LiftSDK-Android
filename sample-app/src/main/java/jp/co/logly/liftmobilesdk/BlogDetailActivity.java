package jp.co.logly.liftmobilesdk;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * An activity representing a single Blog detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BlogListActivity}.
 */
public class BlogDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String extra = intent.getStringExtra(BlogDetailFragment.ARG_ITEM_ID);
            if (extra == null) {
                // if invoked from custom URL schema.
                Uri data = intent.getData();
                if (data != null && data.getHost().equals("jp.co.logly.liftmobilesdk.sample")) {
                    List<String> parts = intent.getData().getPathSegments();
                    if (parts != null && parts.get(0).equals("page") && parts.size() == 2) {
                        extra = parts.get(1);
                    }
                }
            }
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            BlogDetailFragment fragment = new BlogDetailFragment();
            if (extra != null) {
                Bundle arguments = new Bundle();
                arguments.putString(BlogDetailFragment.ARG_ITEM_ID, extra);
                fragment.setArguments(arguments);
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.blog_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, BlogListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
