package com.cp.netllc.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 步骤二
 */
public class Step3Activity extends AppCompatActivity implements OnClickListener {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private Button bt_next;
    private AutoCompleteTextView tv_cb_name;
    private AutoCompleteTextView tv_cb_tel;
    private AutoCompleteTextView tv_cb_relationship;
    private AutoCompleteTextView tv_cb_job;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp3);

        bt_next  =  (Button)findViewById(R.id.bt_next);
        tv_cb_name  =  (AutoCompleteTextView)findViewById(R.id.tv_cb_name);
        tv_cb_tel  =  (AutoCompleteTextView)findViewById(R.id.tv_cb_tel);
        tv_cb_relationship  =  (AutoCompleteTextView)findViewById(R.id.tv_cb_relationship);
        tv_cb_job  =  (AutoCompleteTextView)findViewById(R.id.tv_cb_job);

        bt_next.setOnClickListener(this);
        initData();
    }
    public void initData() {
        if (!TextUtils.isEmpty(AppApplication.content.third_one)) {
            tv_cb_name.setText(AppApplication.content.third_one);
        }
        if (!TextUtils.isEmpty(AppApplication.content.third_two)) {
            tv_cb_tel.setText(AppApplication.content.third_two);
        }
        if (!TextUtils.isEmpty(AppApplication.content.third_three)) {
            tv_cb_relationship.setText(AppApplication.content.third_three);
        }
        if (!TextUtils.isEmpty(AppApplication.content.third_four)) {
            tv_cb_job.setText(AppApplication.content.third_four);
        }

    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(tv_cb_name.getText().toString())){
            Toast toast = Toast.makeText(Step3Activity.this,"姓名不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.third_one=tv_cb_name.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_cb_tel.getText().toString())){
            Toast toast = Toast.makeText(Step3Activity.this,"手提電話不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.third_two=tv_cb_tel.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_cb_relationship.getText().toString())){
            Toast toast = Toast.makeText(Step3Activity.this,"關係不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.third_three=tv_cb_relationship.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_cb_job.getText().toString())){
            Toast toast = Toast.makeText(Step3Activity.this,"職業不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.third_four=tv_cb_job.getText().toString().trim();
        }
        Intent intent = new Intent();
        intent.setClass(this,Step4Activity.class);
        startActivity(intent);
    }
}

