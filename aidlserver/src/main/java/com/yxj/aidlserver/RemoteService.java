package com.yxj.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Yxj
 * Time:    2019/5/22 上午10:48
 * -----------------------------------------
 * Description:
 */
public class RemoteService extends Service {

    List<Person> list = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        list.add(new Person("yxj","杭州",29));
        list.add(new Person("lzl","台北",45));
        list.add(new Person("tlp","华盛顿",70));
        return new RemoteBinder();
    }

    class RemoteBinder extends IRemoteServiceInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public Person getPersonInfoByName(String name) throws RemoteException {
            for (int i = 0; i < list.size(); i++) {
                Person person = list.get(i);
                if(name.equals(person.getName())){
                    return person;
                }
            }
            return null;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
