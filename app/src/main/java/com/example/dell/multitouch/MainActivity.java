package com.example.dell.multitouch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout root;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initView(){
        root = (RelativeLayout)findViewById(R.id.root);
        iv = (ImageView) findViewById(R.id.iv);
        root.setOnTouchListener(new View.OnTouchListener() {

            float currentDistance;
            float lastDistance = -1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("action down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("action move");
                        System.out.println("pointer count: "+ event.getPointerCount());

                        if (event.getPointerCount()>=2){
                            float offsetX = event.getX(0) - event.getX(1);
                            float offsetY = event.getY(0) - event.getY(1);
                            currentDistance = (float) Math.sqrt(offsetX*offsetX+offsetY*offsetY);

                            if (lastDistance<0){
                                lastDistance = currentDistance;
                            }else {
                                if (currentDistance - lastDistance>10){
                                    System.out.println("放大");
                                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    lp.width = (int) (1.1f*iv.getWidth());
                                    lp.height = (int) (1.1f*iv.getHeight());
                                    iv.setLayoutParams(lp);
                                    lastDistance = currentDistance;
                                }else if (lastDistance - currentDistance>10){
                                    System.out.println("缩小");
                                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    lp.width = (int) (0.9f*iv.getWidth());
                                    lp.height = (int) (0.9f*iv.getHeight());
                                    iv.setLayoutParams(lp);
                                    lastDistance = currentDistance;
                                }
                            }
                        }




                        /* RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        lp.leftMargin = (int) event.getX();
                        lp.topMargin = (int) event.getY();
                        iv.setLayoutParams(lp);*/
                        System.out.println(String.format("x1:%f,y1:%f,x2:%f,y2:%f",event.getX(0),event.getY(0),event.getX(1),event.getY(1)));
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("action up");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
