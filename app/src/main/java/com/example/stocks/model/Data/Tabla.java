package com.example.stocks.model.Data;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/*contructor de la clase
 * @param actividad donde va a estar la tabla
 * @param TableLayout donde se va a imprimir la tabla
 * @param*/

public class Tabla {
    private TableLayout tableLayout;
    private String[] header;
    private Context context;
    private TableRow tableRow;
    private TextView txtCell;
    private float[] weightCol;
    /*private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.height= TableRow.LayoutParams.MATCH_PARENT;
        params.width= TableRow.LayoutParams.MATCH_PARENT;
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }*/

    private TableRow.LayoutParams tableRowLayoutParams(float weight){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.height= TableRow.LayoutParams.MATCH_PARENT;
        params.width= TableRow.LayoutParams.MATCH_PARENT;
        params.setMargins(1,1,1,1);
        params.weight=weight;
        return params;
    }

    public Tabla(Context context, TableLayout tableLayout){
        /**CREA TextView QUE SE USARá COMO CELDA DE LA TABLA
         @param String con el contenido de la celda
         @param int con el tamaño del texto de la celda

         */
        this.context = context;
        this.tableLayout = tableLayout;
    }

    public Tabla(Context context, TableLayout tableLayout, float[] weightCol){
        /**CREA TextView QUE SE USARá COMO CELDA DE LA TABLA
         @param String con el contenido de la celda
         @param int con el tamaño del texto de la celda

         */
        this.context = context;
        this.tableLayout = tableLayout;
        this.weightCol = weightCol;
    }

    public Tabla(Context context, TableLayout tableLayout, float[] weightCol, String[] header){
        /**CREA TextView QUE SE USARá COMO CELDA DE LA TABLA
         @param String con el contenido de la celda
         @param int con el tamaño del texto de la celda

         */
        this.context = context;
        this.tableLayout = tableLayout;
        this.weightCol = weightCol;
        this.header = header;
        addHeader();
    }

    private TextView nuevaCelda(String data, int textSize){//, int maxEms){//, int colorFondo){
        txtCell= new TextView(context);
        txtCell.setTextSize(textSize);
        txtCell.setText(data);
        txtCell.setHeight(50);
        //txtCell.setBackgroundColor(colorFondo);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setMaxEms(0);
        return txtCell;
    }

    private TextView nuevaCeldaConMaxEms(String data, int textSize, int maxEms){//, int colorFondo){
        txtCell= new TextView(context);
        txtCell.setTextSize(textSize);
        txtCell.setText(data);
        //txtCell.setBackgroundColor(colorFondo);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setMaxEms(maxEms);
        return txtCell;
    }

    public void addFila(String[] datos){
        int col= 0;
        tableRow= new TableRow(context);
        while (col < datos.length){
            //nuevaCelda(datos[col], 18);//, 1);//weightCol[col], Color.WHITE);
            if (col < weightCol.length) {
                tableRow.addView(nuevaCelda(datos[col], 18), tableRowLayoutParams(weightCol[col]));//, newTableRowParams());
            } else {
                tableRow.addView(nuevaCelda(datos[col], 18), tableRowLayoutParams(1));//, newTableRowParams());
            }
            col++;
        }
        tableLayout.addView(tableRow);
    }

    public void addFilaConMaxEms(String[] datos, int[] maxEms){
        int col= 0;
        tableRow= new TableRow(context);
        while (col < datos.length){
            //nuevaCelda(datos[col], 18);//, 1);//weightCol[col], Color.WHITE);
            tableRow.addView(nuevaCeldaConMaxEms(datos[col], 18, maxEms[col]), tableRowLayoutParams(weightCol[col]));//, newTableRowParams());
            col++;
        }
        tableLayout.addView(tableRow);
    }

/*
    public void addRow2(String[] datos){
        int col= 0;
        newRow();
        while (col < datos.length){
            //nuevaCelda(datos[col], 18, 1);//, Color.WHITE);
            tableRow.addView(nuevaCelda(datos[col], 18, 1), tableRowLayoutParams(weightCol[col]));//, newTableRowParams());
            col++;
        }
        tableLayout.addView(tableRow);
    }
*/

    private void addHeader(){
        int col= 0;
        tableRow= new TableRow(context);
        while (col < header.length) {
            //nuevaCelda(header[col], 18, 1);//, weightCol[col]);//, Color.WHITE);
            tableRow.addView(nuevaCelda(header[col], 18), tableRowLayoutParams(weightCol[col]));
            col++;
        }
        tableLayout.addView(tableRow);
    }

    public void removeAllViews(){
        tableLayout.removeAllViews();
        addHeader();
    }

    public View getRow(int index){

        return tableLayout.getChildAt(index);
    }

    public void colorearFondo(int color){
    }

}

/*<ScrollView
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent">

</ScrollView>*/