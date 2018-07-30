package jp.co.logly.liftmobilesdk

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.logly.Lift.Result.InlineResponse200Items
import jp.co.logly.Lift.WidgetView
import jp.co.logly.liftmobilesdk.data.BlogContent
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [BlogListActivity]
 * in two-pane mode (on tablets) or a [BlogDetailActivity]
 * on handsets.
 */
class BlogDetailFragment : Fragment() {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    val ARG_ITEM_ID = "item_id"

    /**
     * adspot_id & widget_id, ref_url of this sample application.
     * NOTE: ref_url can be empty string in mobile sdk.
     */
    val LOGLY_SAMPLE_ADSPOT_ID: Long = 4228263
    val LOGLY_SAMPLE_WIDGET_ID: Long = 3624
    val LOGLY_SAMPLE_REF_URL = ""

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: BlogContent.BlogItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mItem = BlogContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = mItem?.title
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.item_detail.text = it.details

            val liftWidget = rootView.findViewById(R.id.lift_widget) as WidgetView
            liftWidget.mOnWigetItemClickListener = WidgetView.OnWigetItemClickListener { _widget, url, _item ->
                if (BlogDetailActivity.isDebugMode) {
                    AlertDialog.Builder(context!!)
                            .setTitle("Debug mode.")
                            .setMessage(url)
                            .setPositiveButton("Dismiss", null)
                            .show()
                    return@OnWigetItemClickListener true
                }
                false
            }
            liftWidget.requestByURL(mItem?.url, LOGLY_SAMPLE_ADSPOT_ID, LOGLY_SAMPLE_WIDGET_ID, LOGLY_SAMPLE_REF_URL)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
