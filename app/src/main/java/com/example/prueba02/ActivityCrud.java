package com.example.prueba02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCrud extends AppCompatActivity {
    EditText txt_code, txt_name, txt_description;
    Button btn_save, btn_update, btn_delete, btn_prev;
    List<String> data;
    ArrayList<Form> dataForms;
    ListView listData;

    int selectedId;
    String url = "http://chatdoc.eastus.cloudapp.azure.com:8000/api/form";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        initUI();
        String message = getIntent().getStringExtra("message");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        listWs();
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate();
            }
        });

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Form selectedForm=dataForms.get(i);
                selectedId= selectedForm.getId();
                txt_code.setText(selectedForm.getCode());
                txt_name.setText(selectedForm.getName());
                txt_description.setText(selectedForm.getDescription());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteWs();
                cleanForm();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postFromButtonWs();
                listWs();
                cleanForm();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedWs();
                listWs();
                cleanForm();
            }
        });
    }

    private void initUI() {
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_code = (EditText) findViewById(R.id.txt_code);
        txt_description = (EditText) findViewById(R.id.txt_description);
        listData = (ListView) findViewById(R.id.lv_1);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_prev = (Button) findViewById(R.id.btn_prev);
    }

    private void listWs() {
        //HACER LOS CAMBIOS NECESARIOS PARA OBTENER LOS DATOS Y LISTARLOS
        data = new ArrayList<String>();
        dataForms = new ArrayList<Form>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Form f= new Form();
                            f.setId((int)obj.get("id"));
                            f.setCode(obj.get("code").toString());
                            f.setName(obj.get("name").toString());
                            f.setDescription(obj.get("description").toString());
                            String object = "Código: "+obj.get("code").toString() + "\nNombre: "+obj.get("name").toString() + "\nDescripcion: "+obj.get("description").toString();
                            data.add(object);
                            dataForms.add(f);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(ActivityCrud.this, android.R.layout.simple_list_item_1,data);
                    listData.setAdapter(adapter);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void postFromButtonWs() {
        String code=txt_code.getText().toString();
        String name=txt_name.getText().toString();
        String description=txt_description.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //MOSTRAR TOAST DE QUE FUE ACTUALIZADO CORRECTAMENTE Y ACTUALIZAR LISTA DE FORMS
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("code", code);
                params.put("name", name);
                params.put("description", description);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void updatedWs() {
        //HACER LOS CAMBIOS NECESARIOS PARA ACTUALIZAR EL FORM

        String code=txt_code.getText().toString();
        String name=txt_name.getText().toString();
        String description=txt_description.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.PUT, url+"/"+selectedId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("code", code);
                params.put("name", name);
                params.put("description", description);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void deleteWs() {

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url+"/"+selectedId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service

                listWs();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());

            }
        });
        Volley.newRequestQueue(this).add(deleteRequest);
    }

    public void navigate() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void cleanForm(){
        txt_code.setText("");
        txt_name.setText("");
        txt_description.setText("");
    }
}