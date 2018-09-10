package com.canplay.information.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.canplay.information.R;
import com.canplay.information.base.BaseApplication;
import com.canplay.information.bean.Contacts;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Step5Activity extends AppCompatActivity implements View.OnClickListener, Callback {

    //调用系统相册-选择图片
//    private static final int IMAGE = 1;

    private RelativeLayout rl_cd;
    private RelativeLayout rl_card;
    private RelativeLayout rl_in;
    private RelativeLayout rl_address;
    private Button bt_next;

    private ArrayList<Contacts> list ;

    public Gson mGson = new Gson();

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static String TAG ="Step5Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp5);

        rl_cd = (RelativeLayout)findViewById(R.id.rl_cd);
        rl_card = (RelativeLayout)findViewById(R.id.rl_card);
        rl_in = (RelativeLayout)findViewById(R.id.rl_in);
        rl_address = (RelativeLayout)findViewById(R.id.rl_address);
        bt_next = (Button) findViewById(R.id.bt_next);

        rl_cd.setOnClickListener(this);
        rl_card.setOnClickListener(this);
        rl_in.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        bt_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent=null;
        switch (view.getId()){
            case R.id.rl_cd:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
                break;
          case R.id.rl_card:
              intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
                break;
          case R.id.rl_in:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(intent, 3);
                break;
          case R.id.rl_address:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              startActivityForResult(intent,4);
                break;
            case R.id.bt_next:
//                post((BaseApplication)getApplication()).getUrl(),mGson.toJson(list));
                break;
        }

    }


    String post(String url, String json){
        Log.d("response","start");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = null;
            Call call = client.newCall(request);
            call.enqueue(this);
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //获取图片路径
        if ( resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath,requestCode);
            c.close();
        }

        getContacts();
    }


    //加载图片
    private void showImage(String imaePath,int id){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        switch (id){
            case 1:
                ((ImageView)findViewById(R.id.iv_cd)).setImageBitmap(bm);
                ((ImageView)findViewById(R.id.iv_cd)).setVisibility(View.VISIBLE);
                break;
            case 2:
                ((ImageView)findViewById(R.id.iv_card)).setImageBitmap(bm);
                ((ImageView)findViewById(R.id.iv_card)).setVisibility(View.VISIBLE);
                break;
            case 3:
                ((ImageView)findViewById(R.id.iv_in)).setImageBitmap(bm);
                ((ImageView)findViewById(R.id.iv_in)).setVisibility(View.VISIBLE);
                break;
            case 4:
                ((ImageView)findViewById(R.id.iv_address)).setImageBitmap(bm);
                ((ImageView)findViewById(R.id.iv_address)).setVisibility(View.VISIBLE);
                break;
        }
    }



    private void getContacts() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
//        String[] arr = new String[cursor.getCount()];
        list = new ArrayList<Contacts>(cursor.getCount());
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                Contacts mContacts = new Contacts();
                //获取姓名
//                String name = cursor.getString(1);
                mContacts.setName(cursor.getString(1));
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
//                arr[i] = id + " , 姓名：" + name;

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                        String num = phonesCusor.getString(0);
                        mContacts.setPhoneNumber(num);
                }
                list.add(mContacts);
            } while (cursor.moveToNext());
        }

        Log.d("List:",mGson.toJson(list));

    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "onFailure: "+e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG, "onResponse: "+response.body().string());
    }
}
