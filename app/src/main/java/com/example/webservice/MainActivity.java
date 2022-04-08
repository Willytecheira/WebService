package com.example.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText entrada;
    private Button enviar;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrada = (EditText) findViewById(R.id.entrada);
        enviar = (Button) findViewById(R.id.enviar);
        salida = (TextView) findViewById(R.id.resultado);

        enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.enviar){
            String url = "https://swapi.dev/api/people/"+entrada.getText().toString().trim();
            tarea t = new tarea();
            t.execute(url);
        }
    }
    class tarea extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer str = new StringBuffer();
            try {
                URL url2 = new URL(strings[0]);
                HttpsURLConnection conexion = (HttpsURLConnection) url2.openConnection();
                InputStream input = conexion.getInputStream();
                InputStreamReader reader =  new InputStreamReader(input);
                BufferedReader lectura = new BufferedReader(reader);
                String s;
                while ((s=lectura.readLine()) != null){
                    str.append(s);
                }
                System.out.println(str.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            salida.setText(s);
            super.onPostExecute(s);
        }
    }
}