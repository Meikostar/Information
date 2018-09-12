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
public class Step2Activity extends AppCompatActivity implements OnClickListener {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private Button bt_next;
    private AutoCompleteTextView tv_position;
    private AutoCompleteTextView tv_seniority;
    private AutoCompleteTextView tv_cp_name;
    private AutoCompleteTextView tv_cp_address;
    private AutoCompleteTextView tv_cp_tel;
    private AutoCompleteTextView tv_month_pay;
    private AutoCompleteTextView tv_out_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setp2);

        bt_next = (Button) findViewById(R.id.bt_next);
        tv_position = (AutoCompleteTextView) findViewById(R.id.tv_position);
        tv_seniority = (AutoCompleteTextView) findViewById(R.id.tv_seniority);
        tv_cp_name = (AutoCompleteTextView) findViewById(R.id.tv_cp_name);
        tv_cp_address = (AutoCompleteTextView) findViewById(R.id.tv_cp_address);
        tv_cp_tel = (AutoCompleteTextView) findViewById(R.id.tv_cp_tel);
        tv_month_pay = (AutoCompleteTextView) findViewById(R.id.tv_month_pay);
        tv_out_type = (AutoCompleteTextView) findViewById(R.id.tv_out_type);
        bt_next.setOnClickListener(this);
        initData();
    }

    public void initData() {
        if (!TextUtils.isEmpty(AppApplication.content.second_one)) {
            tv_position.setText(AppApplication.content.second_one);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_two)) {
            tv_seniority.setText(AppApplication.content.second_two);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_three)) {
            tv_cp_name.setText(AppApplication.content.second_three);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_four)) {
            tv_cp_address.setText(AppApplication.content.second_four);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_five)) {
            tv_cp_tel.setText(AppApplication.content.second_five);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_six)) {
            tv_month_pay.setText(AppApplication.content.second_six);
        }
        if (!TextUtils.isEmpty(AppApplication.content.second_seven)) {
            tv_out_type.setText(AppApplication.content.second_seven);
        }
    }

    @Override
    public void onClick(View view) {

        if (TextUtils.isEmpty(tv_position.getText().toString())) {
            Toast toast = Toast.makeText(Step2Activity.this, "職位不能为空", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.second_one=tv_position.getText().toString().trim();
        }
        if (TextUtils.isEmpty(tv_seniority.getText().toString())) {
            Toast toast = Toast.makeText(Step2Activity.this, "年資不能为空", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.second_two=tv_seniority.getText().toString().trim();
        }
        if (TextUtils.isEmpty(tv_cp_name.getText().toString())) {
            Toast toast = Toast.makeText(Step2Activity.this, "公司名稱不能为空", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.second_three=tv_cp_name.getText().toString().trim();
        }
        if (TextUtils.isEmpty(tv_cp_address.getText().toString())) {
            Toast toast = Toast.makeText(Step2Activity.this, "公司地址不能为空", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }else {
            AppApplication.content.second_four=tv_cp_address.getText().toString().trim();
        }
        if(!TextUtils.isEmpty(tv_cp_tel.getText().toString())){
            AppApplication.content.second_five=tv_cp_tel.getText().toString().trim();
        }
        if(!TextUtils.isEmpty(tv_month_pay.getText().toString())){
            AppApplication.content.second_six=tv_month_pay.getText().toString().trim();
        }
        if(!TextUtils.isEmpty(tv_out_type.getText().toString())){
            AppApplication.content.second_seven=tv_out_type.getText().toString().trim();
        }
        Intent intent = new Intent();
        intent.setClass(this, Step3Activity.class);
        startActivity(intent);
    }
}

