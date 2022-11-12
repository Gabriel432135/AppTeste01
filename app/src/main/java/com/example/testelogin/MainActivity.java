package com.example.testelogin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.testelogin.databinding.MainActivityBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private File ff;
    private int total, progresso;
    private Runnable barraProgresso, runfinal, nome;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.login.setOnClickListener(view1 -> {
            startActivity(new Intent(getApplicationContext(), TelaLogin.class));
        });
        binding.criar.setOnClickListener(view1 -> {
            startActivity(new Intent(getApplicationContext(), TelaCriar.class));
        });

        setContentView(view);



        handler = new Handler();

        barraProgresso = () -> {
            binding.progressBar.setProgress(progresso*100/total);
        };

        nome = () -> {
            binding.textView2.setText(ff.getName());
        };

        runfinal = () -> {
            binding.textView2.setText("");
            binding.progressBar.setProgress(0);
        };

        binding.button2.setOnClickListener(view1 -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                new Thread(() -> {

                    File file;
                    file = new File(Environment.getExternalStorageDirectory().toString() + "/Download");
                    File list[] = file.listFiles();
                    if (list != null) {

                        for (int i = 0; i < list.length; i++) {
                            try {
                                File resultado = new File(file.getPath(), "cópia(" + i + ") "+list[i].getName());
                                resultado.createNewFile();

                                InputStream in = new FileInputStream(list[i]);
                                OutputStream out = new FileOutputStream(resultado);

                                byte[] buffer = new byte[1024];
                                int len;
                                progresso = 0;
                                total = in.available();

                                handler.postDelayed(nome, 0);

                                while ((len = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, len);
                                    progresso += len;
                                    ff = list[i];
                                    handler.postDelayed(barraProgresso, 0);
                                }

                                in.close();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.postDelayed(runfinal, 0);
                    }

                    //------------------------------------------------------------------------------------

                    ClasseTeste testeOutputObjeto = new ClasseTeste();
                    testeOutputObjeto.setVar(3);

                    try {
                        FileOutputStream arquivoClasseTeste = new FileOutputStream(Environment.getExternalStorageDirectory().toString()+"/Download/save.act");
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(arquivoClasseTeste);
                        objectOutputStream.writeObject(testeOutputObjeto);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    //--------------------------------------------------------------------------------------
                }).start();
            }
        });
    }



}