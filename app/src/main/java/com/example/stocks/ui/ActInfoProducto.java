package com.example.stocks.ui;

public class ActInfoProducto {//extends AppCompatActivity {
/*
    private AdminDb baseDeDatos;
    private TableLayout tableLayout;
    private Tabla tabls;
    private ArrayList<> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info_producto);

        //ocultando actionBar
        getSupportActionBar().hide();



    }


    public void cargarMovimientos(){

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery("select M."+C_ID_PRODUCTO+", M."+C_FECHA+",  from "+TABLA_MOVIMIENTOS+" M, "+TABLA_COMPRAS+" C,"+TABLA_VENTAS+" V, "+TABLA_PRESTAMOS+" P, where "+ C_ID_PRODUCTO +" = ",null);
        cursor.moveToFirst();

        while (!cursor.isNull()){

            String[] row = {};
            cursor.moveToNext();


        }

        SELECT OrderID, C.CustomerID, CompanyName, OrderDate FROM Customers C, Orders O, Employees E WHERE C.CustomerID = O.CustomerID AND O.EmployeeID = E.EmployeeID







        long registros = DatabaseUtils.longForQuery(db,"SELECT COUNT(*) FROM "+ TABLA_MOVIMIENTOS,null);
        String registro = String.valueOf(registros);
        String[] campos= {C_ID_LINEA, C_NOMBRE_LINEA, C_COLOR};

        try {
            Cursor cursor = db.query(TABLA_LINEAS, campos, null, null, null, null, null);
            if (cursor.moveToFirst()) {

                listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                while (cursor.moveToNext()) {
                    listaLineas.add(new Linea(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                }

            }

    }
*/
}
