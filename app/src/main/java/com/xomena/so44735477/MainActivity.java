package com.xomena.so44735477;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LocalityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.adapter = new LocalityAdapter(getApplicationContext(), this.initSampleLocalities());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(this.adapter);
    }

    private ArrayList<Locality> initSampleLocalities() {
        ArrayList<Locality> res = new ArrayList<Locality>();
        Locality Barcelona = new Locality("Barcelona", 41.385064f, 2.173403f);
        Locality London = new Locality("London", 51.507351f,-0.127758f);
        Locality Zurich = new Locality("Zurich", 47.376887f,8.541694f);
        res.add(Barcelona);
        res.add(London);
        res.add(Zurich);
        return res;
    }
}
