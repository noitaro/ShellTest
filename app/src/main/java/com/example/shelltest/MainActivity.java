package com.example.shelltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.execButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = ((EditText)findViewById(R.id.commandText)).getText().toString();
                String[] data = cmd.split(" ");
                String result = adbComandExe(data);

                TextView tv = findViewById(R.id.outputTextView);
                tv.setText(result);
            }
        });
    }

    private String adbComandExe(String[] command) {

        StringBuilder scolder = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        InputStream iStream = null;
        InputStreamReader isReader = null;

        try{

            Process prc = processBuilder.start();
            iStream = prc.getInputStream();
            isReader = new InputStreamReader(iStream);
            BufferedReader bufferedReader = new BufferedReader(isReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                scolder.append(line);
                scolder.append("\n");
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(iStream != null){
                    iStream.close();
                }
                if(isReader != null){
                    isReader.close();
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        return scolder.toString();
    }
}
