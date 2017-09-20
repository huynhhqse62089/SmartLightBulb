package com.example.huynhhq.smartlightbulb;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.huynhhq.smartlightbulb.Adapter.CustomAdapter;
import com.example.huynhhq.smartlightbulb.Model.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ArrayList<SpinnerModel> CustomListViewValueArr = new ArrayList<>();
    TextView output = null;
    CustomAdapter adapter;
    MainActivity activity = null;
    private Spinner spnLanguage;
    private Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselanguage_layout);
        TextView title_toolbar = (TextView)findViewById(R.id.toolbar_title);
        title_toolbar.setText(R.string.app_name);
        activity = this;
//        config Spinner here
        spnLanguage = (Spinner) findViewById(R.id.spinner_language);

        setListData();

        Resources res = getResources();

        adapter = new CustomAdapter(activity, R.layout.spinner_rows, CustomListViewValueArr, res);

        spnLanguage.setAdapter(adapter);

//        config Button here
        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanBulbsActivity.class);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_go,R.anim.animation_back).toBundle();
                startActivity(intent, bndlanimation);
            }
        });
    }

    private void setListData() {
        String coy[] = {"English", "Vietnamese"};

        for (int i = 0; i < coy.length; i++) {
            final SpinnerModel sched = new SpinnerModel();
            sched.setLanguageName(coy[i]);
            sched.setImage("image" + i);
            CustomListViewValueArr.add(sched);
        }
    }
}
