/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.incallui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.services.telephony.common.ICallCommandService;
import com.android.services.telephony.common.ICallHandlerService;

/**
 * Service used to listen for call state changes.
 */
public class CallHandlerService extends Service {

    private static final String TAG = CallHandlerService.class.getSimpleName();
    private static final boolean DBG = false; // TODO: Have a shared location for this.

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final ICallHandlerService.Stub mBinder = new ICallHandlerService.Stub() {

        @Override
        public void setCallCommandService(ICallCommandService service) {
            logD("onConnected: " + service.toString());
            CallCommandClient.init(service);
        }

        @Override
        public void onIncomingCall(int callId) {
            final Intent intent = new Intent(getApplication(), InCallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        public void onDisconnect(int callId) {
        }
    };

    private void logD(String message) {
        if (DBG) {
            Log.d(TAG, message);
        }
    }
}