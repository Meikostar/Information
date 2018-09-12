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
public class Step4Activity extends AppCompatActivity implements OnClickListener {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private Button bt_next;
    private AutoCompleteTextView tv_re_name;
    private AutoCompleteTextView tv_re_tel;
    private AutoCompleteTextView tv_re_relationship;
    private AutoCompleteTextView tv_re_job;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp4);

        bt_next  =  (Button)findViewById(R.id.bt_next);
        tv_re_name  =  (AutoCompleteTextView)findViewById(R.id.tv_re_name);
        tv_re_tel  =  (AutoCompleteTextView)findViewById(R.id.tv_re_tel);
        tv_re_relationship  =  (AutoCompleteTextView)findViewById(R.id.tv_re_relationship);
        tv_re_job  =  (AutoCompleteTextView)findViewById(R.id.tv_re_job);
        bt_next.setOnClickListener(this);
        initData();
    }
    public void initData() {
        if (!TextUtils.isEmpty(AppApplication.content.fouth_one)) {
            tv_re_name.setText(AppApplication.content.fouth_one);
        }
        if (!TextUtils.isEmpty(AppApplication.content.fouth_two)) {
            tv_re_tel.setText(AppApplication.content.fouth_two);
        }
        if (!TextUtils.isEmpty(AppApplication.content.fouth_three)) {
            tv_re_relationship.setText(AppApplication.content.fouth_three);
        }
        if (!TextUtils.isEmpty(AppApplication.content.fouth_four)) {
            tv_re_job.setText(AppApplication.content.fouth_four);
        }

    }


    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(tv_re_name.getText().toString())){
            Toast toast = Toast.makeText(Step4Activity.this,"姓名不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.fouth_one=tv_re_name.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_re_tel.getText().toString())){
            Toast toast = Toast.makeText(Step4Activity.this,"手提電話不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.fouth_two=tv_re_tel.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_re_relationship.getText().toString())){
            Toast toast = Toast.makeText(Step4Activity.this,"關係不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.fouth_three=tv_re_relationship.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tv_re_job.getText().toString())){
            Toast toast = Toast.makeText(Step4Activity.this,"職業不能为空",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.fouth_four=tv_re_job.getText().toString().trim();
        }
        Intent intent = new Intent();
        intent.setClass(this,Step5Activity.class);
        startActivity(intent);
    }


}

