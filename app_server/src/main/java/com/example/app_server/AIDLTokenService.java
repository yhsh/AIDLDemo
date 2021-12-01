package com.example.app_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aidldemo.ITokenAidlInterface;


/**
 * @author xiayiye5
 * @date 2021/11/30 16:11
 */
public class AIDLTokenService extends Service {
    private static final String TAG = "AIDLTokenService";
    ITokenAidlInterface.Stub stub = new ITokenAidlInterface.Stub() {

        @Override
        public void postLoginToken(String accessToken) throws RemoteException {
            Log.d(TAG, "获取到的登录token：" + accessToken);
            Intent intent = new Intent(AIDLTokenService.this, ServerActivity.class);
            intent.putExtra("accessToken", accessToken);
            startActivity(intent);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
