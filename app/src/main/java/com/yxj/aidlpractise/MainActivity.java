package com.yxj.aidlpractise;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yxj.aidlserver.IRemoteServiceInterface;
import com.yxj.aidlserver.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IRemoteServiceInterface remoteServiceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conncet();
            }
        });
        findViewById(R.id.btn_get_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
            }
        });
    }

    private void getInfo() {
        if(remoteServiceInterface!=null){
            try {
                Person person = remoteServiceInterface.getPersonInfoByName("yxj");
                Toast.makeText(this,"查询到person："+person.toString(),Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteServiceInterface = IRemoteServiceInterface.Stub.asInterface(service);
            Log.e("yxj","client onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void conncet() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PRACTISE_AIDL");
        intent = getExplicitIntent(intent);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    private Intent getExplicitIntent(Intent intent) {
        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentServices(intent,0);

        if(resolveInfoList == null || resolveInfoList.isEmpty()){
            return null;
        }
        ResolveInfo resolveInfo = resolveInfoList.get(0);
        String pkgName = resolveInfo.serviceInfo.packageName;
        String className = resolveInfo.serviceInfo.name;
        Log.e("yxj","packageName = " + pkgName);
        Log.e("yxj","className = " + className);
        ComponentName componentName = new ComponentName(pkgName,className);
        return intent.setComponent(componentName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null)
            unbindService(connection);
    }
}
