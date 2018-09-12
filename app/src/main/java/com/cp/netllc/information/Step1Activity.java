package com.cp.netllc.information;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.netllc.information.timeselect.TimeSelectorDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * 步骤一
 */
public class Step1Activity extends AppCompatActivity implements OnClickListener {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private Button bt_next;
    private AutoCompleteTextView tv_date;
    private AutoCompleteTextView tv_en_name;
    private AutoCompleteTextView tv_cd_num;
    private AutoCompleteTextView tv_age;
    private AutoCompleteTextView tv_address;
    private AutoCompleteTextView tv_house_type;
    private AutoCompleteTextView tv_house_tel;
    private AutoCompleteTextView tv_house_date;
    private AutoCompleteTextView tv_cn_name;
    private TextInputLayout ll_birth;
    private TimeSelectorDialog selectorDialog;
    public void showDailog(){
        if (selectorDialog == null) {
            selectorDialog = new TimeSelectorDialog(this);
        }
        selectorDialog.setDate(new Date(System.currentTimeMillis()))
                .setBindClickListener(new TimeSelectorDialog.BindClickListener() {
                    @Override
                    public void time(String time) {
                        tv_date.setText(time);
                    }
                });
        selectorDialog.show(ll_birth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp1);

        bt_next  =  (Button)findViewById(R.id.bt_next);
        tv_date  =  (AutoCompleteTextView)findViewById(R.id.tv_date);
        tv_cn_name  =  (AutoCompleteTextView)findViewById(R.id.tv_cn_name);
        tv_en_name  =  (AutoCompleteTextView)findViewById(R.id.tv_en_name);
        tv_cd_num  =  (AutoCompleteTextView)findViewById(R.id.tv_cd_num);
        tv_age  =  (AutoCompleteTextView)findViewById(R.id.tv_age);
        tv_address  =  (AutoCompleteTextView)findViewById(R.id.tv_address);
        tv_house_type  =  (AutoCompleteTextView)findViewById(R.id.tv_house_type);
        tv_house_tel  =  (AutoCompleteTextView)findViewById(R.id.tv_house_tel);
        tv_house_date  =  (AutoCompleteTextView)findViewById(R.id.tv_house_date);
        ll_birth  =  (TextInputLayout)findViewById(R.id.ll_birth);



        bt_next.setOnClickListener(this);
        ll_birth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }
        });
        tv_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }
        });
        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        populateAutoComplete();
//
//        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });
//
//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }


    @Override
    public void onClick(View view) {

        if(TextUtils.isEmpty(tv_cn_name.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"中文姓名不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.first_one=tv_cn_name.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_en_name.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"英文姓名不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.first_two=tv_en_name.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_cd_num.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"身份證號碼不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
         if(tv_cd_num.getText().toString().length()==18||tv_cd_num.getText().toString().length()==15){
             AppApplication.content.first_three=tv_cd_num.getText().toString().trim();
         }else {
             Toast toast = Toast.makeText(Step1Activity.this,"身份證號碼错误",Toast.LENGTH_SHORT);
             toast.show();
             return;
         }
        }
        if(TextUtils.isEmpty(tv_date.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"出身年月不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.first_four=tv_date.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_age.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"年齡不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.first_five=tv_age.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_address.getText().toString())){
            Toast toast = Toast.makeText(Step1Activity.this,"住址不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.first_six=tv_address.getText().toString().trim();
        }

        tv_house_type  =  (AutoCompleteTextView)findViewById(R.id.tv_house_type);
        tv_house_tel  =  (AutoCompleteTextView)findViewById(R.id.tv_house_tel);
        tv_house_date  =  (AutoCompleteTextView)findViewById(R.id.tv_house_date);
        if(!TextUtils.isEmpty(tv_house_type.getText().toString())){
            AppApplication.content.first_seven=tv_house_type.getText().toString().trim();
        }
        if(!TextUtils.isEmpty(tv_house_tel.getText().toString())){
            AppApplication.content.first_eight=tv_house_tel.getText().toString().trim();
        }
        if(!TextUtils.isEmpty(tv_house_date.getText().toString())){
            AppApplication.content.first_night=tv_house_date.getText().toString().trim();
        }
        Intent intent = new Intent();
        intent.setClass(this,Step2Activity.class);
        startActivity(intent);
    }

    /**
     * 检查身份证号码合法性
     * @param idCardNo
     * @return
     * @throws Exception
     */
    public boolean checkIdCardNo(String idCardNo) {
        try {

            int length = idCardNo.length();
            if(length == 15){
                Pattern p = Pattern.compile("^[0-9]*$");
                Matcher m = p.matcher(idCardNo);
                return m.matches();
            }else if(length == 18){
                String front_17 = idCardNo.substring(0, idCardNo.length() - 1);//号码前17位
                String verify = idCardNo.substring(17, 18);//校验位(最后一位)
                Pattern p = Pattern.compile("^[0-9]*$");
                Matcher m = p.matcher(front_17);
                if(!m.matches()){
                    return false;
                }else{
                    return this.checkVerify(front_17, verify);
                }
            }else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 校验验证位合法性
     * @param front_17
     * @param verify
     * @return
     * @throws Exception
     */
    public static boolean checkVerify(String front_17,String verify) throws Exception{
        try {
            int[] wi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
            String[] vi = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
            int s = 0;
            for(int i = 0; i<front_17.length(); i++){
                int ai = Integer.parseInt(front_17.charAt(i) + "");
                s += wi[i]*ai;
            }
            int y = s % 11;
            String v = vi[y];
            if(!(verify.toUpperCase().equals(v))){
                return false;
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}

