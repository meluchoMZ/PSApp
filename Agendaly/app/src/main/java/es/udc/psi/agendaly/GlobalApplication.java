package es.udc.psi.agendaly;

import android.app.Application;
import android.content.Context;

import butterknife.ButterKnife;

public class GlobalApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        initButterKnifeDebug();
    }

    private void initButterKnifeDebug() {

        ButterKnife.setDebug(BuildConfig.DEBUG);
    }

    public static Context getContext() {
        return context;
    }
}
