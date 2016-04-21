package com.javen.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.javen.recyclerview.adapter.MRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private List<String> titles;
    private List<Integer> pics;
    private LinearLayoutManager layoutManager;
    private MRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    int lastVisibleItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiperefresh_activity);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshWidget= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);


        // 创建一个线性布局管理器
        layoutManager = new LinearLayoutManager(this);
        //设置垂直滚动，也可以设置横向滚动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //RecyclerView设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);

        //RecyclerView设置Adapter
        mAdapter=new MRecyclerViewAdapter(this, titles, pics);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshWidget.setColorSchemeColors(R.color.color1, R.color.color2,
                R.color.color3, R.color.color4);

        mSwipeRefreshWidget.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                System.out.println("lastVisibleItem:"+lastVisibleItem);
            }
        });


        mAdapter.setOnItemClickLitener(new MRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SwipeRefreshActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(SwipeRefreshActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                if (position % 2 ==0 ){
                    mAdapter.addData(position);
                }else {
                    mAdapter.removeData(position);
                }

            }
        });
    }

    public void initData(){
        //item 显示所需
        String[] title = {"Blog :http://www.cnblogs.com/zyw-205520.",
                "A good laugh and a long sleep are the best cures in the doctor's book.",
                "all or nothing, now or never ",
                "Be nice to people on the way up, because you'll need them on your way down.",
                "Be confident with yourself and stop worrying what other people think. Do what's best for your future happiness!",
                "Blessed is he whose fame does not outshine his truth.",
                "Blessed is he whose fame does not outshine his truth.",
                "Blessed is he whose fame does not outshine his truth.",
                "Create good memories today, so that you can have a good past"
        };
        titles=new ArrayList<>();
        for(int i=0;i<title.length;i++){
            titles.add(i,title[i]);
        }
        /**
         * 图片资源版权归属于Smartisan.com
         */
        int[] pic = {R.drawable.wapvideo_49,R.drawable.wapvideo_50,R.drawable.wapvideo_51,R.drawable.wapvideo_57,R.drawable.wapvideo_58,R.drawable.wapvideo_59,R.drawable.wapvideo_65,R.drawable.wapvideo_66,R.drawable.wapvideo_67};
        pics=new ArrayList<>();
        for(int i=0;i<pic.length;i++){
            pics.add(i,pic[i]);
        }
    }


    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 3000);

        System.out.println("onRefresh()。。。。。");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mSwipeRefreshWidget.setRefreshing(false);
            mAdapter.addData(0);
        }
    };
}
