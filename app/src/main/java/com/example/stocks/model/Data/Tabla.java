package com.example.stocks.model.Data;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/*contructor de la clase
 * @param actividad donde va a estar la tabla
 * @param tabla donde se va a imprimir la tabla
 * @param*/
public class Tabla {
    private TableLayout tableLayout;
    private String title;
    private boolean isTitleEmpty;
    private String[] header;
    private Context context;
    private TableRow tableRow;
    private TextView txtCell;
    private int filas;
    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }

    public Tabla(Context context, TableLayout tableLayout, String title, String[] header){
        this.context = context;
        this.tableLayout = tableLayout;
        this.title = title;
        addTitle(this.title);
        isTitleEmpty = false;
        this.header = header;
        filas = 0;
        addHeader();
    }

    public Tabla(Context context, TableLayout tableLayout, String[] header){
        this.context = context;
        this.tableLayout = tableLayout;
        isTitleEmpty = true;
        this.header = header;
        filas = 0;
        addHeader();
    }

    public Tabla(Context context, TableLayout tableLayout) {
        this.tableLayout = tableLayout;
        this.context = context;
        isTitleEmpty = true;
    }

    public void newRow(){
        tableRow= new TableRow(context);
    }


    public void newCellDefault(String data){
        txtCell= new TextView(context);
        txtCell.setText(data);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(18);
        txtCell.setMinWidth(10);
        txtCell.setMaxWidth(150);
    }

    public void newCell2(String data, int textSize, int minWidth, int maxWidth ){
        txtCell= new TextView(context);
        txtCell.setText(data);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(textSize);
        txtCell.setMinWidth(minWidth);
        txtCell.setMaxWidth(maxWidth);
    }

    public void newCell(String data, int textSize, int width){
        txtCell= new TextView(context);
        txtCell.setText(data);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(textSize);
        txtCell.setWidth(width);
    }

    public void addRow(String[] datos){
        int col= 0;
        newRow();
        while (col<datos.length){
            if (col < 2){
                newCell(datos[col], 18,10);


            }
            /*if (col == 1){ //hacer mas ancho el campo de nombres
                newCellDefault(datos[col]);
                txtCell.setMinWidth(10);
                txtCell.setMaxWidth(100);
            }*/else {
                newCellDefault(datos[col]);}
            col++;
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
        filas++;
    }

    public void addTitle(String titulo){
        newRow();
        newCell2(titulo, 14,10,200);
        tableRow.addView(txtCell,newTableRowParams());
        tableLayout.addView(tableRow, 0);
        filas++;
    }

    private void addHeader(){
        int indexC= 0;
        newRow();
        while (indexC < header.length){
            newCellDefault(header[indexC]);
            indexC++;
            tableRow.addView(txtCell,newTableRowParams());
        }
        if (isTitleEmpty) {

            tableLayout.addView(tableRow, 0);

        } else {

            tableLayout.addView(tableRow, 1);

        }
        filas++;
    }

    public void removeAllViews(){
        tableLayout.removeAllViews();
        if (!isTitleEmpty){
            addTitle(this.title);
        }
        addHeader();
    }

    public void colorearFondo(int color){

    }

}