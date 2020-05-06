package com.example.stocks.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.stocks.R;

import java.util.Calendar;

public class ActAgregarCliente extends AppCompatActivity {

    private EditText editNacimiento;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_agregar_cliente);

        //ocultando actionBar
        getSupportActionBar().hide();

        editNacimiento = (EditText) findViewById(R.id.AC_editText_nacimiento);
  /*      editNacimiento.setOnClickListener(new View.OnClickListener(){
            public void OnClick(View view){
                Calendar calendario= Calendar.getInstance();
                int year= calendario.get(Calendar.YEAR);
                int month= calendario.get(Calendar.MONTH);
                int day= calendario.get(Calendar.DATE);




                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        getApplicationContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
*/



    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
