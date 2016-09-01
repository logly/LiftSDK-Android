package jp.co.logly.liftmobilesdk;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import jp.co.logly.Lift.WidgetView;
import jp.co.logly.liftmobilesdk.data.BlogContent;

/**
 * A fragment representing a single Blog detail screen.
 * This fragment is either contained in a {@link BlogListActivity}
 * in two-pane mode (on tablets) or a {@link BlogDetailActivity}
 * on handsets.
 */
public class BlogDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private BlogContent.BlogItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BlogDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = BlogContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blog_detail, container, false);

        if (mItem != null) {
            TextView textView = (TextView) rootView.findViewById(R.id.blog_detail);
            textView.setText(mItem.details);

            WidgetView liftWidget = (WidgetView) rootView.findViewById(R.id.lift_widget);
            liftWidget.requestByURL(mItem.url, 4228263, 3624, "http://blog.logly.co.jp/");
        }

        return rootView;
    }
}
