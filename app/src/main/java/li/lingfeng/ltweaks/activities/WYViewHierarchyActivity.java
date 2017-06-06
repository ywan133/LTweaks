package li.lingfeng.ltweaks.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by yangwan on 04/06/2017.
 */

public class WYViewHierarchyActivity extends AppCompatActivity {

    private Receiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if (!getIntent().getAction().equals(Intent.ACTION_VIEW) || !getIntent().getType().equals("data/view")) {
            Toast.makeText(this, R.string.not_supported, Toast.LENGTH_SHORT).show();
            finish();
            Logger.toast_i(this, "不好使");
            Logger.e("不好使");
            return;
        }
        */

//        Logger.toast_i(this, "好使");
//        Logger.e("好使");

        IntentFilter filter = new IntentFilter("com.wy.listen_log");
        receiver = new Receiver();
        this.registerReceiver(receiver, filter);


        setContentView(R.layout.wy_act_view_hack);

        processExtras(getIntent().getExtras());    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            processExtras(arg1.getExtras());

        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processExtras(intent.getExtras());
    }

    private void processExtras(Bundle extra){
        try{
            String result = (String) extra.get(Intent.EXTRA_TEXT);
            Logger.e(result);

            TextView tv = (TextView) findViewById(R.id.sh_wy_display_log);
            String str =  tv.getText().toString();
            String display =str + result;
            tv.setText(display);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }
}
