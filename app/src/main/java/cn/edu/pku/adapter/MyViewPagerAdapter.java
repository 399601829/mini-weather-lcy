package cn.edu.pku.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Yue on 2016/11/29.
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public MyViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    /* 更新视图 */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /* 销毁某个元素时候调用 */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    /* 返回视图的个数 */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /* 判断是否为生成的对象 */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
