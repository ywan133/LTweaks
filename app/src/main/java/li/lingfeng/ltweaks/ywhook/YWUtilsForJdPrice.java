package li.lingfeng.ltweaks.ywhook;

import android.app.Activity;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yangwan on 13/06/2017.
 */

public class YWUtilsForJdPrice {

    public static void invokeJdPriceHistory(String foundProductId, Activity mActivity){

        JSONObject node = new JSONObject();
        try {
            node.put("product_id", foundProductId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setClassName(
                // Your app's package name
                "li.lingfeng.ltweaks",
                // The full class name of the activity you want to start
                "li.lingfeng.ltweaks.activities.JDHistoryActivity");
        intent.setType("data/view");

        intent.putExtra(Intent.EXTRA_TEXT, node.toString());
        mActivity.startActivity(intent);

    }
}
