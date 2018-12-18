package jp.co.logly.Lift;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jp.co.logly.ApiInvoker.ApiException;
import jp.co.logly.ApiInvoker.R;
import jp.co.logly.Lift.Result.InlineResponse200;
import jp.co.logly.Lift.Result.InlineResponse200Items;

/**
 * Simple Logly Lift Wdiget view Class.
 */
public class WidgetView extends LinearLayout {
    private Context mContext;
    private GridView mGridView;
    private ResponseArrayAdaptor mAdaptor;
    private String mURL;
    private HashSet<Integer> mSentBeaconIndexes = new HashSet<Integer>();

    /**
     * Interface definition for a callback to be invoked when an item in this
     * WidgetView has been clicked.
     */
    public interface OnWigetItemClickListener {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         *
         * @param widget The WidgetView where the click happened.
         * @param url clicked item's landing URL. (shortcut for item.ldUrl )
         * @param item clicked item's data.
         * @return boolean whether Listener handled click action. when false, WidgetView will open system browser.
         */
        boolean onItemClick(WidgetView widget, String url, InlineResponse200Items item);
    }

    public OnWigetItemClickListener mOnWigetItemClickListener = null;

    public WidgetView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public WidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public WidgetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * Internal Class: DownloadImageTask
     **/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> weakBmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.weakBmImage = new WeakReference<>(bmImage);
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            if (url.startsWith("http")) {   // only support 'http' or 'https'
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("DownloadImageTask", e.getMessage());
                    e.printStackTrace();
                }
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            ImageView bmImage = weakBmImage.get();
            if (bmImage != null) {
                bmImage.setImageBitmap(result);
            }
        }
    }

    /**
     * Internal Class: ResponseArrayAdaptor
     **/
    class ResponseArrayAdaptor extends ArrayAdapter<InlineResponse200Items> {
        public ResponseArrayAdaptor(Context context, int resource, List<InlineResponse200Items> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lift_widget_cell, parent, false);
            }

            InlineResponse200Items item = getItem(position);
            if (item == null) {
                return convertView;
            }

            TextView textView = (TextView) convertView.findViewById(R.id.Title);
            textView.setText(item.getTitle());
            textView = (TextView) convertView.findViewById(R.id.Text);
            textView.setText("");
            if (item.getIsArticle() != null && item.getIsArticle() != 1 && item.getAdvertisingSubject() != null) {
                textView.setText("PR: " + item.getAdvertisingSubject());
            }

            if (item.getImageUrl() != null) {
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                new DownloadImageTask(imageView).execute(resolveUrl(item.getImageUrl()));
            }
            if (item.getBeaconUrl() != null && !mSentBeaconIndexes.contains(position)) {
                new DownloadImageTask(null).execute(resolveUrl(item.getBeaconUrl()));
                mSentBeaconIndexes.add(position);
            }

            return convertView;
        }

        private String resolveUrl(String url) {
            int index = mURL.indexOf(":");
            String protocol = mURL.substring(0, index+ 1);
            if (!protocol.startsWith("http")) {
                protocol = "https:";
            }
            if (url.startsWith("//")) {     // protocol relative URL.
                url = protocol + url;
            }
            return url;
        }
    }

    private static class ClickAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... url) {
            try {
                new java.net.URL(url[0]).getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

     /**
     * @see android.view.View(Context, AttributeSet, int)
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        View layout = LayoutInflater.from(context).inflate(R.layout.lift_widget, this);

        mAdaptor = new ResponseArrayAdaptor(mContext, 0, new ArrayList<InlineResponse200Items>());
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(mAdaptor);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InlineResponse200Items item = mAdaptor.getItem(position);

                // click tracking.
                new ClickAsyncTask().execute(item.getUrl());

                // click callback.
                String url = item.getLdUrl();
                if (url == null) { url = item.getUrl(); }
                if (mOnWigetItemClickListener != null) {
                    Boolean handled = mOnWigetItemClickListener.onItemClick(WidgetView.this, url, item);
                    if (handled) {
                        return;
                    }
                }

                // open browser.
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(browserIntent);
            }
        });
    }

    private static class RequestAsyncTask extends AsyncTask<Void, Void, InlineResponse200> {
        private String mURL;
        private long mAdspotID;
        private long mWidgetID;
        private String mRef;
        private WeakReference<ResponseArrayAdaptor> mAdaptor;

        RequestAsyncTask(final String inURL, final long inAdspotID, final long inWidgetID, final String inRef, final ResponseArrayAdaptor inAdaptor) {
            mURL = inURL;
            mAdspotID = inAdspotID;
            mWidgetID = inWidgetID;
            mRef = inRef;
            mAdaptor = new WeakReference<>(inAdaptor);
        }

        @Override
        protected InlineResponse200 doInBackground(Void... params) {
            InlineResponse200 result = null;
            try {
                result = new DefaultApi().requestLift(mAdspotID, mWidgetID, mURL, mRef, "items");
            } catch (ApiException e) {
                Log.e("AsyncLiftRequest", e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(InlineResponse200 result) {
            ResponseArrayAdaptor adaptor = mAdaptor.get();
            if (adaptor == null) { return; }

            adaptor.clear();
            if (result != null && result.getItems() != null)
                adaptor.addAll(result.getItems());
        }

    }

    /**
     * start request Lift recommendation data.
     * @param inURL page URL as recommendation key.
     * @param inAdspotID Lift adspot ID
     * @param inWidgetID Lift wiget ID
     * @param inRef Referrer URL (usually empty for Mobile user).
     */
    public void requestByURL(final String inURL, final long inAdspotID, final long inWidgetID, final String inRef) {
        mURL = inURL;
        mSentBeaconIndexes.clear();

        new RequestAsyncTask(inURL, inAdspotID, inWidgetID, inRef, mAdaptor).execute();
    }

 }
