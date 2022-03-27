package com.example.aula_1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener {
    Context context;
    // Declara o método construtor da classe GPStracker.
    public GPStracker(Context c){
        context = c;
    }
    // Declara o método "getLocation()".
    public Location getLocation(){
        // Verifica a permissão para localizar o dispositivo.
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Se não foi permitido, escreve uma mensagem na tela e retorna nada.
            Toast.makeText(context, "Não foi Permitido", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Se o GPS estiver ativado, solicita atualização da localização, armazena e depois retorna a última localização conhecida.
        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        } else {
            // Se o GPS estiver desativado, escreve uma mensagem na tela.
            Toast.makeText(context, "Porfavor, habilitar o GPS", Toast.LENGTH_LONG).show();
        }
        // Retorna nada.
        return null;
    }
    // Sobrescreve o método onProviderDisabled (Obrigatório).
    @Override
    public void onProviderDisabled(@NonNull String provider) { }
    // Sobrescreve o método onLocationChanged (Obrigatório).
    @Override
    public void onLocationChanged(@NonNull Location location) { }
    // Sobrescreve o método onStatusChanged (Obrigatório).
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }
}
