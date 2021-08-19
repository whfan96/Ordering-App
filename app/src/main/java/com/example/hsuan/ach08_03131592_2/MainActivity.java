package com.example.hsuan.ach08_03131592_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView gdvFood;
    Button btnNext,btnBack,btnOk,btnBackHome;
    TextView txtFood;
    ImageView imvFood,img01,img02;
    int now = -1;
    int[] foods = {R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,
            R.drawable.food7,R.drawable.food8,R.drawable.food9,R.drawable.food10,R.drawable.food11,R.drawable.food12};
    String[] foodId = {"A01","A02","B01","D01","C02","B02","B03","A03","C02","C03","C04","C05"};
    String[] foodName = {"蛋","魚","麵包","起司","甜甜圈","麵","飯","肉","薯條","漢堡","披薩","熱狗"};
    String[] foodEnglishName = {"Egg","Fish","Bread", "Cheese", "Donut", "Noodles", "Rice", "Meat", "French fries", "Hamburger", "Pizza", "Hot dog"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        img01 = (ImageView) findViewById(R.id.img01);
        registerForContextMenu(img01);
        img02 = (ImageView) findViewById(R.id.img02);
        registerForContextMenu(img02);

        gdvFood = (GridView) findViewById(R.id.gdvFood);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnBackHome = (Button) findViewById(R.id.btnBackHome);
        txtFood = (TextView) findViewById(R.id.txtFood);
        imvFood = (ImageView) findViewById(R.id.imvFood);
        registerForContextMenu(imvFood);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now++;
                now%=foods.length;
                imvFood.setImageResource(foods[now]);
                txtFood.setText("食物編號："+foodId[now]+"\r\n食物名稱："+foodName[now]+"\r\nFood Name："+foodEnglishName[now]);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now--;
                if(now<0){
                    now=foods.length-1;
                }
                else{
                    now%=foods.length;
                }
                imvFood.setImageResource(foods[now]);
                txtFood.setText("食物編號："+foodId[now]+"\r\n食物名稱："+foodName[now]+"\r\nFood Name："+foodEnglishName[now]);

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(now<0){
                    Toast.makeText(v.getContext(), "請選擇食物喔", Toast.LENGTH_SHORT).show();
                }
                else{
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("確認訂購")
                            .setMessage("食物編號："+foodId[now]+"\r\n食物名稱："+foodName[now]+"\r\nFood Name："+foodEnglishName[now])
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(), "確定送出", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(v.getContext(), "取消送出", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }

            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent it = new Intent();
                it.setClass(MainActivity.this  , MainActivity.class);
                startActivity(it);
            }
        });


        MyAdapter ada = new MyAdapter(MainActivity.this);
        gdvFood.setAdapter(ada);
        gdvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                now = position;
                now%=foods.length;
                imvFood.setImageResource(foods[now]);
                txtFood.setText("食物編號："+foodId[now]+"\r\n食物名稱："+foodName[now]+"\r\nFood Name："+foodEnglishName[now]);
            }
        });

    }

    class MyAdapter extends BaseAdapter {

        Context sCtx;
        MyAdapter(Context ctx){
            sCtx = ctx;
        }

        @Override
        public int getCount() {
            return foods.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //TextView txt = new TextView(sCtx);
            //txt.setText(foods[position]);
            //return txt;

            ImageView imv = new ImageView(sCtx);
            imv.setImageResource(foods[position]);
            imv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imv.setLayoutParams(new GridView.LayoutParams(150,150));
            return imv;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == imvFood){
            Toast.makeText(v.getContext(), "別碰我TAT", Toast.LENGTH_SHORT).show();
        }
        if(v==img01){
            Toast.makeText(MainActivity.this, "hi, 我是img01", Toast.LENGTH_SHORT).show();
        }
        if(v==img02){
            Toast.makeText(MainActivity.this, "hi~ 我是img02", Toast.LENGTH_SHORT).show();
        }
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
        if (id == R.id.MeanuAbout) {
            Toast.makeText(MainActivity.this, "關於", Toast.LENGTH_SHORT).show();
            //return true;
        }
        if (id == R.id.MeanuLOut) {
            finish();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
