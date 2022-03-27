package com.example.aula_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Declaração da variável de imagem e botão
    private ImageView imageView;
    private Button btnGeo;
    // Sobrescreve o método onCreate, porém também executa o código já existente no "onCreate()" da classe pai e o aplicativo abre no layout principal.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Atriubue o botão de id "btn_geo" a variável btnGeo e pede permissão para acessar a localizaÇão do celular.
        btnGeo = (Button) findViewById(R.id.btn_geo);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        // Executa um evento ao ser pressionado.
        btnGeo.setOnClickListener(new View.OnClickListener() {
            // Sobrescreve o método onClick da classe pai e utiliza a aplicação GPStracker e pega a localização do dispositivo.
            @Override
            public void onClick(View view) {
                GPStracker g = new GPStracker(getApplication());
                Location l = g.getLocation();
                // Se a localização for diferente de nula, pega a Latitude e Longitude e escreve na tela.
                if(l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LATITUDE: " + lat + "\nLONGITUDE: " + lon, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Verifica a permissão para usar a câmera, se não foi permitido, solicita a permissão.
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 0);
        }
        // Atriubue a imagem de id "img_photo" a variável imageView e encontra um botão com o id "btn_photo" e executa um evento ao ser pressionado.
        imageView = (ImageView) findViewById(R.id.img_photo);
        findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            // Sobrescreve o método onClick da classe pai com a função "tirarFoto()".
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });
    }
    // Declara a função "firarFoto()", abre a câmera e retorna a foto tirada.
    private void tirarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    // Sobreescreve o onActivityResult da classe pai.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        // Se o "requestCode" for igual ao criado anteriormente e o usuário confirmar, transforma a foto tirada em um bitmap e substitui a imagem e chama o método onActivityResult da classe pai.
        if(requestCode == 1 & resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}