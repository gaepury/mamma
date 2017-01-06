package com.mommatest.pc.momma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mommatest.pc.momma.application.ApplicationController;
import com.mommatest.pc.momma.network.NetworkService;

public class FragmentMain extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView Gohome;
    private int index =0;
    private Button mybut;
    ImageView search_go;
    EditText searchbar_ed;
    NetworkService networkService;

    private static final String TAG_TAB = "TAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchbar);
        networkService = ApplicationController.getInstance().getNetworkService();

        search_go = (ImageView)findViewById(R.id.searchbargo);
        searchbar_ed = (EditText)findViewById(R.id.searchbar_ed);

        search_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchintent = new Intent(getApplicationContext(), SearchResult.class);
                searchintent.putExtra("searchresult", searchbar_ed.getText().toString());
                searchintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                searchintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(searchintent);
            }
        });

        mybut = (Button)findViewById(R.id.mybut);
        mybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mypage = new Intent(getApplicationContext(), MyPage.class);
                mypage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mypage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mypage);
            }
        });

            Gohome = (TextView) findViewById(R.id.Gohome);
            tabLayout = (TabLayout) findViewById(R.id.tablayout);
            tabLayout.addTab(tabLayout.newTab().setText("홈"));
            tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
            tabLayout.addTab(tabLayout.newTab().setText("성분"));

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        Gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome = new Intent(getApplicationContext(), FragmentMain.class);
                gohome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                gohome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(gohome);
            }
        });
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getFragmentManager(), tabLayout.getTabCount(),this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        Fragment_search fragment_search = new Fragment_search();
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.hide(fragment_search);
//        ft.replace(R.id.searchbar, fragment_search);
//        ft.commit();



    }
}