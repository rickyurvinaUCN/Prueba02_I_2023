package com.example.prueba02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Button btn_save, btn_update, btn_delete;
    List<String> data;
    ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_code = (EditText) findViewById(R.id.txt_code);
        txt_description = (EditText) findViewById(R.id.txt_description);
        listData = (ListView) findViewById(R.id.lv_1);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
    }

    private void listWs() {
        //HACER LOS CAMBIOS NECESARIOS PARA OBTENER LOS DATOS Y LISTARLOS
        String url = "http://20.231.202.18:8000/api/form";
        data = new ArrayList<String>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
    private void postFromButtonWs(final String code, final String name) {
        String url = "http://192.168.1.189:8000/api/projects";
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

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
    private void updatedWs(final String code, final String name) {
        //HACER LOS CAMBIOS NECESARIOS PARA ACTUALIZAR EL FORM

        String url = "http://20.231.202.18:8000/api/form";

        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
    private void deleteWs() {
        String url = "http://20.231.202.18:8000/api/form";

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());

            }
        });
        Volley.newRequestQueue(this).add(deleteRequest);
    }
}