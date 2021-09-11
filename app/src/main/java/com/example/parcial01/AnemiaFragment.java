package com.example.parcial01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnemiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnemiaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String nombre, apellido, Correo, cedula;
    private String sexo="";
    private Double nHemo;
    private Integer edad=0;
    private RadioButton r1, r2;
    private Button btn_save, btn_update, btn_search, btn_delete;
    private EditText et_nombre, et_apellido,et_hemo, et_correo, et_edad, et_cedula;

    public AnemiaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnemiaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnemiaFragment newInstance(String param1, String param2) {
        AnemiaFragment fragment = new AnemiaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_anemia,container,false);
        btn_save = root.findViewById(R.id.btn_save);
        btn_search = root.findViewById(R.id.btn_search);
        btn_update = root.findViewById(R.id.btn_update);
        btn_delete = root.findViewById(R.id.btn_delete);

        et_nombre = root.findViewById(R.id.et_nombre);
        et_apellido = root.findViewById(R.id.et_apellido);
        et_cedula = root.findViewById(R.id.et_cedula);
        et_hemo = root.findViewById(R.id.et_hemo);
        et_edad = root.findViewById(R.id.et_edad);
        et_correo = root.findViewById(R.id.et_correo);

        r1 = root.findViewById(R.id.r1);
        r2 = root.findViewById(R.id.r2);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = et_nombre.getText().toString();
                apellido = et_apellido.getText().toString();
                cedula = et_cedula.getText().toString();
                nHemo = Double.parseDouble(et_hemo.getText().toString());
                Correo = et_correo.getText().toString();
                edad = Integer.parseInt(et_edad.getText().toString());
                if(nombre.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar un nombre", Toast.LENGTH_SHORT).show();
                }else if(apellido.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar un apellido", Toast.LENGTH_SHORT).show();
                }else if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else if(nHemo == 0){
                    Toast.makeText(getContext(), "Se debe ingresar un nivel de hemoglobina", Toast.LENGTH_SHORT).show();
                }else if(Correo.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar un correo", Toast.LENGTH_SHORT).show();
                }else if(edad == 0){
                    Toast.makeText(getContext(), "Se debe ingresar una edad", Toast.LENGTH_SHORT).show();
                }else{
                    if(r1.isChecked()){
                        sexo="M";
                    }else {
                        sexo="F";
                    }
                    Boolean positivo = validarAnemia(nHemo,edad,sexo);
                    insertar(nombre,apellido, cedula, nHemo, Correo, edad, sexo, positivo);
                    if(positivo){
                        showMessage(nombre,apellido);
                    }
                    et_nombre.setText("");
                    et_apellido.setText("");
                    et_cedula.setText("");
                    et_hemo.setText("");
                    et_edad.setText("");
                    et_correo.setText("");
                }
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cedula = et_cedula.getText().toString();
                if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else{
                    consultar(cedula);
                }

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = et_nombre.getText().toString();
                apellido = et_apellido.getText().toString();
                cedula = et_cedula.getText().toString();
                nHemo = Double.parseDouble(et_hemo.getText().toString());
                Correo = et_correo.getText().toString();
                edad = Integer.parseInt(et_edad.getText().toString());
                if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean positivo = validarAnemia(nHemo,edad,sexo);
                    actualizar(nombre,apellido, cedula, nHemo, Correo, edad, sexo, positivo);
                    if(positivo){
                        showMessage(nombre,apellido);
                    }
                    et_nombre.setText("");
                    et_apellido.setText("");
                    et_cedula.setText("");
                    et_hemo.setText("");
                    et_edad.setText("");
                    et_correo.setText("");
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cedula = et_cedula.getText().toString();
                if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else{
                    eliminar(cedula);
                    et_nombre.setText("");
                    et_apellido.setText("");
                    et_cedula.setText("");
                    et_hemo.setText("");
                    et_edad.setText("");
                    et_correo.setText("");
                }
            }
        });


        return root;
    }

    public void insertar(String nombre, String apellido, String cedula,  Double nHemo, String correo, Integer edad, String sexo, Boolean positivo){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("edad", edad);
        registro.put("sexo", sexo);
        registro.put("nHemo", nHemo);
        registro.put("cedula", cedula);
        registro.put("correo", correo);
        registro.put("positivo", positivo);
        bd.insert("usuario2", null, registro);
        bd.close();
        Toast.makeText(getContext(), "Se cargaron los datos usuario "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
    }

    public void consultar(String cedula){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre, apellido, cedula, edad, sexo, nHemo, correo from usuario2 where cedula=" + cedula, null);
        if (fila.moveToFirst()) {
            et_nombre.setText(fila.getString(0));
            et_apellido.setText(fila.getString(1));
            et_cedula.setText(fila.getString(2));
            et_edad.setText(fila.getString(3));
            String sexo = (fila.getString(4));
            if (sexo.equals("M")){
                r1.setChecked(true);
            }else{
                r2.setChecked(true);
            }
            et_hemo.setText(fila.getString(5));
            et_correo.setText(fila.getString(6));

        } else
            Toast.makeText(getContext(), "No existe el usuario con cedula "+cedula, Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void actualizar(String nombre, String apellido, String cedula,  Double nHemo, String correo, Integer edad, String sexo, Boolean positivo){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("edad", edad);
        registro.put("sexo", sexo);
        registro.put("nHemo", nHemo);
        registro.put("cedula", cedula);
        registro.put("correo", correo);
        registro.put("positivo", positivo);
        int cant = bd.update("usuario2", registro, "cedula=" + cedula, null);
        bd.close();
        if (cant == 1){
            Toast.makeText(getContext(), "Se modificaron los datos del usuario "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "No existe un usuario con la cedula "+cedula, Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(String cedula){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(),"hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete("usuario2", "cedula=" + cedula, null);
        bd.close();
        if (cant == 1){
            Toast.makeText(getContext(), "Se borró el usuario con cedula "+cedula, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "No existe un usuario con la cedula "+cedula, Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean validarAnemia (Double nHemo, Integer edad, String sexo){
        Boolean response = false;
        if (edad >= 0 && edad <=1){
            if (nHemo >= 13.0 && nHemo <= 26){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > 1 && edad <= 6){
            if (nHemo >= 10.0 && nHemo <= 18.0){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > 6 && edad <= 12){
            if (nHemo >= 11.0 && nHemo <= 15.0){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > 12 && edad <= (12*5)){
            if (nHemo >= 11.5 && nHemo <= 15.0){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > (12*5) && edad <= (12*10)){
            if (nHemo >= 12.6 && nHemo <= 15.5){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > (12*15) && sexo.equals("F")){
            if (nHemo >= 12.0 && nHemo <= 16.0){
                response = false;
            }else{
                response = true;
            }
        }else if(edad > (12*15) && sexo.equals("M")){
            if (nHemo >= 14.0 && nHemo <= 18.0){
                response = false;
            }else{
                response = true;
            }
        }
        return response;
    }

    public void showMessage(String nombre, String apellido){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención");
        builder.setMessage("Estimado "+nombre+" "+apellido+" usted es potivo para anemia");
        builder.setIcon(R.drawable.warning);
        builder.setPositiveButton("Continuar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}