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
import java.math.BigDecimal;
import java.util.ArrayList;
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
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
            InlineResponse200Items item = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lift_widget_cell, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.Lead);
            textView.setText(item.getLead());
            textView = (TextView) convertView.findViewById(R.id.Text);
            if (item.getIsArticle() != 1 && item.getAdvertisingSubject() != null) {
                textView.setText("PR: " + item.getAdvertisingSubject());
            } else {
                textView.setText(item.getTitle());
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            new DownloadImageTask(imageView).execute(resolveUrl(item.getImageUrl()));
            new DownloadImageTask(null).execute(resolveUrl(item.getBeaconUrl()));

            return convertView;
        }

        private String resolveUrl(String url) {
            int index = mURL.indexOf(":");
            String protocol = mURL.substring(0, index+ 1);
            if (url.startsWith("//")) {     // protocol relative URL.
                url = protocol + url;
            }
            return url;
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
                String url = item.getUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(browserIntent);

                // click tracking.
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... url) {
                        try {
                            new java.net.URL(url[0]).getContent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(item.getLdUrl());
            }
        });
    }

    /**
     * start request Lift recomendation data.
     * @param inURL
     * @param inAdspotID
     * @param inWidgetID
     * @param inRef
     */
    public void requestByURL(final String inURL, final long inAdspotID, final long inWidgetID, final String inRef) {
        mURL = inURL;
        new AsyncTask<Void, Void, InlineResponse200>() {

            @Override
            protected InlineResponse200 doInBackground(Void... params) {
                InlineResponse200 result = null;
                try {
                    result = new DefaultApi().requestLift(inAdspotID, inWidgetID, inURL, inRef, "items");
                } catch (ApiException e) {
                    Log.e("AsyncLiftRequest", e.getMessage());
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(InlineResponse200 result) {
                mAdaptor.clear();
                if (result != null && result.getItems() != null)
                    mAdaptor.addAll(result.getItems());
            }

        }.execute();
    }

 }
