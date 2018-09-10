package com.canplay.information.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.canplay.information.R;
import com.canplay.information.bean.DATA;
import com.canplay.information.view.banner.BannerBaseAdapter;

import java.util.List;


public class BannerAdapter extends BannerBaseAdapter {
    private Context mContext;
    private List<DATA> list;
    private int type=0;//1是用药记录item

    public BannerAdapter(Context context) {
        super(context);
    }


    public interface ItemCliks{
        void getItem(DATA menu, int type);//type 1表示点击事件2 表示长按事件
    }
    private ItemCliks listener;
    public void setClickListener(ItemCliks listener){
        this.listener=listener;
    }



    @Override
    protected int getLayoutResID() {

        return R.layout.banner_item;
    }
    private ImageView img;
    private int imgs;
    @Override
    protected void convert(View convertView, Object data) {
        imgs= (int) data;
        img=convertView.findViewById(R.id.iv_img);
        img.setImageResource(imgs);
    }


}
