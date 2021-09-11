package com.example.parcial01;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HemoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Spinner spinner;
    private Button btn_save, btn_update, btn_search, btn_delete;
    private EditText et_nombre, et_apellido,et_cedula, et_eps;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer select=0;
    private String nombre, apellido, cedula, eps;

    public HemoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HemoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HemoFragment newInstance(String param1, String param2) {
        HemoFragment fragment = new HemoFragment();
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
        View root = inflater.inflate(R.layout.fragment_hemo,container,false);

        btn_search = root.findViewById(R.id.btn_search);
        btn_save = root.findViewById(R.id.btn_save);
        btn_update = root.findViewById(R.id.btn_update);
        btn_delete = root.findViewById(R.id.btn_delete);

        et_nombre = root.findViewById(R.id.et_nombre);
        et_apellido = root.findViewById(R.id.et_apellido);
        et_cedula = root.findViewById(R.id.et_cedula);
        et_eps = root.findViewById(R.id.et_eps);
        spinner = root.findViewById(R.id.spinner);


        String[]opciones={"Seleccionar...","Cuadro neurovegetativos","Trastornos de conciencia","Signos de deshidratación","Sepsis","patologías agudas cardiovascular neurológica"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner.setAdapter(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = et_nombre.getText().toString();
                apellido = et_apellido.getText().toString();
                cedula = et_cedula.getText().toString();
                eps = et_eps.getText().toString();
                select = Integer.parseInt(String.valueOf(spinner.getSelectedItemId()));
                if(nombre.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar un nombre", Toast.LENGTH_SHORT).show();
                }else if(apellido.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar un apellido", Toast.LENGTH_SHORT).show();
                }else if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else if(eps.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una eps", Toast.LENGTH_SHORT).show();
                }else{
                    insertar(nombre,apellido,cedula,eps,select);
                    et_nombre.setText("");
                    et_apellido.setText("");
                    et_cedula.setText("");
                    et_eps.setText("");
                    spinner.setSelection(0);
                    if (select != 0){
                        validaSintoma();
                    }
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
                eps = et_eps.getText().toString();
                select = Integer.parseInt(String.valueOf(spinner.getSelectedItemId()));
                if(cedula.isEmpty()){
                    Toast.makeText(getContext(), "Se debe ingresar una cedula", Toast.LENGTH_SHORT).show();
                }else{
                    actualizar(nombre,apellido,cedula,eps,select);
                    et_nombre.setText("");
                    et_apellido.setText("");
                    et_cedula.setText("");
                    et_eps.setText("");
                    spinner.setSelection(0);
                    if (select != 0){
                        validaSintoma();
                    }
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
                    et_eps.setText("");
                    spinner.setSelection(0);
                }
            }
        });
        return root;
    }

    public void insertar(String nombre, String apellido, String cedula, String eps, Integer opt){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("cedula", cedula);
        registro.put("eps", eps);
        registro.put("sintoma", opt);
        bd.insert("usuario", null, registro);
        bd.close();
        Toast.makeText(getContext(), "Se cargaron los datos usuario "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
    }

    public void consultar(String cedula){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre, apellido, cedula, eps, sintoma from usuario where cedula=" + cedula, null);
        if (fila.moveToFirst()) {
            et_nombre.setText(fila.getString(0));
            et_apellido.setText(fila.getString(1));
            et_cedula.setText(fila.getString(2));
            et_eps.setText(fila.getString(3));
            spinner.setSelection(fila.getInt(4));
        } else
            Toast.makeText(getContext(), "No existe el usuario con cedula "+cedula, Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void actualizar(String nombre, String apellido, String cedula, String eps, Integer opt){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "hemoheart", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("eps", eps);
        registro.put("sintoma", opt);
        int cant = bd.update("usuario", registro, "cedula=" + cedula, null);
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
        int cant = bd.delete("usuario", "cedula=" + cedula, null);
        bd.close();
        if (cant == 1){
            Toast.makeText(getContext(), "Se borró el usuario con cedula "+cedula, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "No existe un usuario con la cedula "+cedula, Toast.LENGTH_SHORT).show();
        }
    }

    public void validaSintoma (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención");
        builder.setMessage("Estimado usuario usted declaro un sintoma, por lo cual debe proceder a realizarce un examen de glicemia");
        builder.setIcon(R.drawable.warning);
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                glicemiaExamen();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void glicemiaExamen(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        Button btn_send = mView.findViewById(R.id.btn_okay);
        EditText et_exam = mView.findViewById(R.id.et_exam);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tittle="";
                String response="";
                Double valueEx = Double.parseDouble(et_exam.getText().toString());
                if (valueEx >= 7.0 && valueEx <= 13.8){
                    tittle = "HIPERGLICEMIA AISLADA:";
                    response = "Indicar glucemia en ayunas y TGP en pacientes sin diagnóstico.\n" +
                            "- Si deshidratación, rehidratación oral o EV según las demandas.\n " +
                            "- Reevaluar conducta terapéutica en diabéticos y cumplimiento de los pilares.\n " +
                            "- Reevaluar dosis de hipoglucemiantes.";
                }else if(valueEx >= 13.8 && valueEx < 33){
                    tittle = "CETOACIDOSIS DIABÉTICA:";
                    response = "Coordinar traslado y comenzar tratamiento.\n" +
                            " - Hidratación con Solución salina 40 ml/Kg en las primeras 4 horas. 1-2 L la primera hora.\n" +
                            " - Administrar potasio al restituirse la diuresis o signos de hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T).\n" +
                            " - Evitar insulina hasta desaparecer signos de hipopotasemia.\n" +
                            " - Administrar insulina simple 0,1 U/kg EV después de hidratar";
                }else if (valueEx >= 33){
                    tittle = "ESTADO HIPEROSMOLAR HIPERGLUCÉMICO NO CETÓSICO:";
                    response = "Coordinar traslado y comenzar tratamiento.\n" +
                            "- Hidratación con Solución Salina 10-15 ml/Kg/h hasta conseguir estabilidad hemodinámica.\n" +
                            "- Administrar potasio al restituirse la diuresis o signos de hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T).";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(tittle);
                builder.setMessage(response);
                builder.setIcon(R.drawable.warning);
                builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}