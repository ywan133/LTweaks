package li.lingfeng.ltweaks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import li.lingfeng.ltweaks.R;
import li.lingfeng.ltweaks.utils.Logger;

/**
 * Created by yangwan on 04/06/2017.
 */

public class WYViewHierarchyActivity extends AppCompatActivity {


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



        setContentView(R.layout.wy_act_view_hack);

        try {
            String result = (String) getIntent().getExtras().get(Intent.EXTRA_TEXT);
            Logger.e(result);

            TextView tv = (TextView) findViewById(R.id.sh_wy_display_log);
            tv.setText(result);


        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
