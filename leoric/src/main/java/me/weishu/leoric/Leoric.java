/*
 * Original Copyright 2015 Mars Kwok
 * Modified work Copyright (c) 2020, weishu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.weishu.leoric;

import static android.os.Build.VERSION.SDK_INT;
import static me.weishu.leoric.LogUtil.leoricLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.blankj.utilcode.util.ProcessUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import me.weishu.reflection.Reflection;

public class Leoric {

    private static final String TAG = "Leoric";

    private LeoricConfigs mConfigurations;

    private Leoric(LeoricConfigs configurations) {
        this.mConfigurations = configurations;
    }

    public static void init(Context base, LeoricConfigs configurations) {
        int res = Reflection.unseal(base);
        leoricLog("SDK_INT:"+SDK_INT+" 反射结果："+res);
        Leoric client = new Leoric(configurations);
        client.initDaemon(base);
    }


    private final String DAEMON_PERMITTING_SP_FILENAME = "d_permit";
    private final String DAEMON_PERMITTING_SP_KEY = "permitted";


    private BufferedReader mBufferedReader;

    private void initDaemon(Context base) {
        if (!isDaemonPermitting(base) || mConfigurations == null) {
            leoricLog("无权限或配置不对");
            return;
        }

//        String processName = getProcessName();
        String processName = ProcessUtils.getCurrentProcessName();
        String packageName = base.getPackageName();

        leoricLog("processName: "+processName);
        leoricLog("mConfigurations.PERSISTENT_CONFIG.processName: "+mConfigurations.PERSISTENT_CONFIG.processName);
        leoricLog("mConfigurations.DAEMON_ASSISTANT_CONFIG.processName: "+mConfigurations.DAEMON_ASSISTANT_CONFIG.processName);
        leoricLog("packageName: "+packageName);
        if (processName.equalsIgnoreCase(mConfigurations.PERSISTENT_CONFIG.processName)) {
            leoricLog("onPersistentCreate");
            ILeoricProcess.Fetcher.fetchStrategy().onPersistentCreate(base, mConfigurations);
        } else if (processName.equalsIgnoreCase(mConfigurations.DAEMON_ASSISTANT_CONFIG.processName)) {
            leoricLog("onDaemonAssistantCreate");
            ILeoricProcess.Fetcher.fetchStrategy().onDaemonAssistantCreate(base, mConfigurations);
        } else if (processName.equalsIgnoreCase(packageName)) {
            leoricLog("onInit");
            ILeoricProcess.Fetcher.fetchStrategy().onInit(base);
        }

        releaseIO();
    }


    private String getProcessName() {
        try {
            File file = new File("/proc/self/cmdline");
            mBufferedReader = new BufferedReader(new FileReader(file));
            return mBufferedReader.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void releaseIO() {
        if (mBufferedReader != null) {
            try {
                mBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mBufferedReader = null;
        }
    }

    private boolean isDaemonPermitting(Context context) {
        SharedPreferences sp = context.getSharedPreferences(DAEMON_PERMITTING_SP_FILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(DAEMON_PERMITTING_SP_KEY, true);
    }

    protected boolean setDaemonPermiiting(Context context, boolean isPermitting) {
        SharedPreferences sp = context.getSharedPreferences(DAEMON_PERMITTING_SP_FILENAME, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(DAEMON_PERMITTING_SP_KEY, isPermitting);
        return editor.commit();
    }

}
