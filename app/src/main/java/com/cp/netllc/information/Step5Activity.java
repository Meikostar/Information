package com.cp.netllc.information;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cp.netllc.information.permission.PermissionConst;
import com.cp.netllc.information.permission.PermissionFail;
import com.cp.netllc.information.permission.PermissionGen;
import com.cp.netllc.information.permission.PermissionSuccess;
import com.cp.netllc.information.timeselect.UploadUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Step5Activity extends AppCompatActivity implements View.OnClickListener, Callback {

    //调用系统相册-选择图片
//    private static final int IMAGE = 1;

    private RelativeLayout rl_cd;
    private RelativeLayout rl_card;
    private RelativeLayout rl_in;
    private RelativeLayout rl_address;
    private Button bt_next;
    private ImageView iv_cd;
    private ImageView iv_card;
    private ImageView iv_in;
    private ImageView iv_address;

    private ArrayList<Contacts> list;

    public Gson mGson = new Gson();

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static String TAG = "Step5Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp5);

        rl_cd = (RelativeLayout) findViewById(R.id.rl_cd);
        rl_card = (RelativeLayout) findViewById(R.id.rl_card);
        rl_in = (RelativeLayout) findViewById(R.id.rl_in);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        bt_next = (Button) findViewById(R.id.bt_next);
        iv_cd = (ImageView) findViewById(R.id.iv_cd);
        iv_card = (ImageView) findViewById(R.id.iv_card);
        iv_in = (ImageView) findViewById(R.id.iv_in);
        iv_address = (ImageView) findViewById(R.id.iv_address);

        rl_cd.setOnClickListener(this);
        rl_card.setOnClickListener(this);
        rl_in.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        if (!isFirst) {
            getContacts();
            isFirst = true;
        }
        initData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardSuccess() {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, type);
    }

    @PermissionFail(requestCode = PermissionConst.REQUECT_CODE_CAMERA)
    public void requestSdcardFailed() {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, type);
    }

    private int type = 1;

    public void getPermiss() {
        PermissionGen.with(Step5Activity.this)
                .addRequestCode(PermissionConst.REQUECT_CODE_CAMERA)
                .permissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.rl_cd:
                type = 1;
                getPermiss();
                break;
            case R.id.rl_card:
                type = 2;
                getPermiss();
                break;
            case R.id.rl_in:
                type = 3;
                getPermiss();
                break;
            case R.id.rl_address:
                type = 4;
                getPermiss();
                break;
            case R.id.bt_next:
                HashMap<String, String> params = new HashMap<>();
                if (!TextUtils.isEmpty(AppApplication.content.first_one)) {
                    params.put("idcard", AppApplication.content.first_one);
                }if (!TextUtils.isEmpty(AppApplication.content.first_two)) {
                params.put("chName", AppApplication.content.first_two);
                }if (!TextUtils.isEmpty(AppApplication.content.first_three)) {
                params.put("enName", AppApplication.content.first_three);
            }if (!TextUtils.isEmpty(AppApplication.content.first_four)) {
                params.put("birthday", AppApplication.content.first_four);
            }if (!TextUtils.isEmpty(AppApplication.content.first_five)) {
                params.put("age",AppApplication.content.first_five );
            }if (!TextUtils.isEmpty(AppApplication.content.first_six)) {
                params.put("address",AppApplication.content.first_six );
            }if (!TextUtils.isEmpty(AppApplication.content.first_seven)) {
                params.put("homeType", AppApplication.content.first_seven);
            }if (!TextUtils.isEmpty(AppApplication.content.first_eight)) {
                params.put("telephone",AppApplication.content.first_eight );
            }if (!TextUtils.isEmpty(AppApplication.content.first_night)) {
                params.put("residence", AppApplication.content.first_night);
            }if (!TextUtils.isEmpty(AppApplication.content.second_one)) {
                params.put("position",AppApplication.content.second_one );
            }if (!TextUtils.isEmpty(AppApplication.content.second_two)) {
                params.put("annualSalary",AppApplication.content.second_two );
            }if (!TextUtils.isEmpty(AppApplication.content.second_three)) {
                params.put("company",AppApplication.content.second_three );
            }if (!TextUtils.isEmpty(AppApplication.content.second_four)) {
                params.put("companyAddress",AppApplication.content.second_four );
            }if (!TextUtils.isEmpty(AppApplication.content.second_five)) {
                params.put("companyPhone", AppApplication.content.second_five);
            }if (!TextUtils.isEmpty(AppApplication.content.second_six)) {
                params.put("salary", AppApplication.content.second_six);
            }if (!TextUtils.isEmpty(AppApplication.content.second_seven)) {
                params.put("salaryType", AppApplication.content.second_seven);
            }if (!TextUtils.isEmpty(AppApplication.content.third_one)) {
                params.put("cohabitName", AppApplication.content.third_one);
            }if (!TextUtils.isEmpty(AppApplication.content.third_two)) {
                params.put("cohabitPhone",AppApplication.content.third_two );
            }if (!TextUtils.isEmpty(AppApplication.content.third_three)) {
                params.put("cohabitRelation",AppApplication.content.third_three );
            }if (!TextUtils.isEmpty(AppApplication.content.third_four)) {
                params.put("cohabitPosition", AppApplication.content.third_four);
            }if (!TextUtils.isEmpty(AppApplication.content.fouth_one)) {
                params.put("relativeName",AppApplication.content.fouth_one );
            }if (!TextUtils.isEmpty(AppApplication.content.fouth_two)) {
                params.put("relativePhone",AppApplication.content.fouth_two );
            }if (!TextUtils.isEmpty(AppApplication.content.fouth_three)) {
                params.put("relativePosition",AppApplication.content.fouth_three );
            }if (!TextUtils.isEmpty(AppApplication.content.fouth_four)) {
                params.put("relativeRelation",AppApplication.content.fouth_four );
            }


                post(((AppApplication) getApplication()).getUrl(), params);
                updateAvator(files, 0);
                break;
        }

    }


    String post(String url, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            try {
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        String params = tempParams.toString();
        Log.d("response", "start");
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder()
                .url(url+"/wx/SaveInformation")
                .post(body)
                .get()
                .build();
        Response response = null;
        Call call = client.newCall(request);
        call.enqueue(this);
        return null;
    }

    private boolean img1;
    private boolean img2;
    private boolean img3;
    private boolean img4;
    private File file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //获取图片路径
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath, requestCode);
            showProgress("上传中...");
            updateAvator(new File(imagePath), requestCode);

            c.close();
        }


    }

    private boolean isFirst;

    public void initData() {

        Glide.with(this).load(AppApplication.content.url_one).into(iv_cd);
        Glide.with(this).load(AppApplication.content.url_two).into(iv_card);
        Glide.with(this).load(AppApplication.content.url_three).into(iv_in);
        Glide.with(this).load(AppApplication.content.url_four).into(iv_address);

    }

    //加载图片
    private void showImage(String imaePath, int id) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        switch (id) {
            case 1:
                AppApplication.content.url_one = imaePath;
                Glide.with(this).load(imaePath).into(iv_cd);
                ((ImageView) findViewById(R.id.iv_cd)).setVisibility(View.VISIBLE);
                break;
            case 2:
                AppApplication.content.url_two = imaePath;
                Glide.with(this).load(imaePath).into(iv_card);
                ((ImageView) findViewById(R.id.iv_card)).setVisibility(View.VISIBLE);
                break;
            case 3:
                AppApplication.content.url_three = imaePath;
                Glide.with(this).load(imaePath).into(iv_in);
                ((ImageView) findViewById(R.id.iv_in)).setVisibility(View.VISIBLE);
                break;
            case 4:
                AppApplication.content.url_four = imaePath;
                Glide.with(this).load(imaePath).into(iv_address);
                ((ImageView) findViewById(R.id.iv_address)).setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * @param context 上下文对象
     * @param dir     存储目录
     * @return
     */
    public String getFilePath(Context context, String dir) {
        String directoryPath = "";
//判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
// directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
        } else {
//没内存卡就存机身内存
            directoryPath = context.getFilesDir() + File.separator + dir;
// directoryPath=context.getCacheDir()+File.separator+dir;
        }
        files = new File(directoryPath);
        if (!files.exists()) {//判断文件目录是否存在
            try {
                files.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        LogUtil.i("filePath====>"+directoryPath);
        return directoryPath;
    }

    private String mFilename;
    private File files;

//    public void saveCrimes(final ArrayList<Contacts> crimes) throws JSONException,
//            IOException {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                JSONArray array = new JSONArray();
//                for (Contacts c : crimes)
//                    array.put(c.toString());
//                File dir = new File("./saved.json");
//                if (!dir.exists())
//                    dir.mkdir();
//                files  = new File("./saved.json");
//                ObjectOutputStream objectOutput = null;
//                try{
//                    objectOutput = new ObjectOutputStream(new FileOutputStream(files));
//                    objectOutput.writeObject(array.toString());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//
//    }

    public void writeTxtToFile(ArrayList<Contacts> crimes) {
                        JSONArray array = new JSONArray();
                for (Contacts c : crimes)
                    array.put(c.toString());
        String filePath = null;

       getFilePath(this,"cont.txt");

        // 每次写入时，都换行写
        String strContent = array.toString() + "\r\n";
        try {

            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }
    private void crFilewriteData(ArrayList<Contacts> crimes) {
        String directoryPath = "";

        directoryPath = getFilesDir() + File.separator;
        try {
            file = new File(directoryPath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
        files = new File(directoryPath + "json.txt");
        if (!files.exists()) {
            try {
                //按照指定的路径创建文件夹
                files.createNewFile();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        if (!files.exists()) {


            FileOutputStream outStream = null;
            JSONArray array = new JSONArray();
            for (Contacts c : crimes)
                array.put(c.toString());
            try {
                outStream = new FileOutputStream(files);
                outStream.write(array.toString().getBytes());
                outStream.close();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }
        }
    }
//    public void saveCrimes(ArrayList<Contacts> crimes) throws JSONException,
//            IOException {
//        mFilename = getFilePath(this, "contact.json");
//
//        JSONArray array = new JSONArray();
//        for (Contacts c : crimes)
//            array.put(c.toString());
//
//        Writer writer = null;
//        try {
//            OutputStream out = openFileOutput(mFilename,
//                    Context.MODE_PRIVATE);
//            writer = new OutputStreamWriter(out);
//            writer.write(array.toString());
//        } finally {
//            if (writer != null) {
//                writer.close();
//            }
//        }
//
//        updateAvator(files, 0);
//    }
    private void getContacts() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
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
                String[] phoneProjection = new String[]{
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
        writeTxtToFile(list);
        Log.d("List:", mGson.toJson(list));

    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "onFailure: " + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG, "onResponse: " + response.body().string());
    }

    private Map<String, String> map = new HashMap<>();
    private Map<String, File> maps = new HashMap<>();
    private String path = "";

    public void updateAvator(File file, final int type) {

        map.clear();
        map.put("idcard", AppApplication.content.first_three);
        map.put("platform", "1");
        map.put("times", System.currentTimeMillis() + "");
        maps.clear();
        if (type != 0) {
            map.put("type", type + "");
            maps.put("img", file);

            path = "/wx/savePhoto";
        } else {
            maps.put("address", file);
            path = "/wx/saveAddressBook";
        }


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String url = "";
                try {
                    url = UploadUtil.UpLoadImg(Step5Activity.this, path, map, maps);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onStart();
                subscriber.onNext(url);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        dimessProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dimessProgress();
                    }

                    @Override
                    public void onNext(String s) {
                        if (type == 1) {
                            img1 = true;
                        } else if (type == 2) {
                            img2 = true;
                        } else if (type == 3) {
                            img3 = true;
                        } else if (type == 4) {
                            img4 = true;
                        }
                        dimessProgress();
//                        BASE info = mGson.fromJson(s, BASE.class);


                    }
                });

    }

    ProgressDialog pd;

    //进度条
    public void showProgress(String msg) {
        if (pd == null) {
            pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
            pd.setCanceledOnTouchOutside(false);
        }
        if (msg == null) {
            msg = "加载中...";
        }
        pd.setMessage(msg);
        pd.show();
    }

    public void dimessProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }
}
