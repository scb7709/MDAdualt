package com.headlth.management.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by abc on 2016/10/9.
 */
public class ImageAdapter extends PagerAdapter {
    private ArrayList<View> viewlist;

    public ImageAdapter(ArrayList<View> viewlist) {
        this.viewlist = viewlist;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return 10000;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= viewlist.size();
        if (position<0){
            position = viewlist.size()+position;
        }
        View view = viewlist.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }
}
