package com.javen.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.javen.recyclerview.adapter.MRecyclerViewAdapter;
import com.javen.recyclerview.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> titles;
    private List<Integer> pics;

    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private MRecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        // 创建一个线性布局管理器
        layoutManager = new LinearLayoutManager(this);
        //设置垂直滚动，也可以设置横向滚动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //另外两种显示模式
        //  mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); Grid视图
          mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)); //这里用线性宫格显示 类似于瀑布流

        //RecyclerView设置布局管理器
//        mRecyclerView.setLayoutManager(layoutManager);

        //RecyclerView设置Adapter
//        adapter = new RecyclerViewAdapter(this, titles, pics);
        mAdapter=new MRecyclerViewAdapter(this, titles, pics);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mAdapter.setOnItemClickLitener(new MRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " long click",
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
        titles=new ArrayList<String>();
        for(int i=0;i<title.length;i++){
            titles.add(i,title[i]);
        }
        /**
         * 图片资源版权归属于Smartisan.com
         */
        int[] pic = {R.drawable.wapvideo_49,R.drawable.wapvideo_50,R.drawable.wapvideo_51,R.drawable.wapvideo_57,R.drawable.wapvideo_58,R.drawable.wapvideo_59,R.drawable.wapvideo_65,R.drawable.wapvideo_66,R.drawable.wapvideo_67};
        pics=new ArrayList<Integer>();
        for(int i=0;i<pic.length;i++){
            pics.add(i,pic[i]);
        }
    }


}
