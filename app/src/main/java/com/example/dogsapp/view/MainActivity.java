package com.example.dogsapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.dogsapp.R;

public class MainActivity extends AppCompatActivity {

    private NavController mNavController;
    private static final int PERMISSION_SEND_SMS = 1111;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        mNavController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,mNavController);


    }

    public void  checkSmsPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

                new AlertDialog.Builder(this)
                        .setTitle("Send SMS Permission")
                        .setMessage("This app requires access to send a SMS")
                        .setPositiveButton("Ask Me", (dialogInterface, i) -> {

                            requestSmsPermission();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {

                            notifyDetailFragment(false);
                        })
                        .show();

            }else {
                requestSmsPermission();
            }
        }else {
            notifyDetailFragment(true);
        }
    }

    private void requestSmsPermission() {

        String[] permissions = {Manifest.permission.SEND_SMS};
        ActivityCompat.requestPermissions(this,permissions,PERMISSION_SEND_SMS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    notifyDetailFragment(true);

                }else {

                    notifyDetailFragment(false);
                }
                break;
        }
    }

    public void notifyDetailFragment(boolean permissionGranted){

        Fragment activeFragment = mFragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if (activeFragment instanceof DetailFragment){
            ((DetailFragment)activeFragment).onPermissionResult(permissionGranted);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, (DrawerLayout) null);
    }
}
