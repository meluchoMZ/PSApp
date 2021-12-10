package es.udc.psi.agendaly;

import android.app.Application;

import butterknife.ButterKnife;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        initButterKnifeDebug();
    }

    private void initButterKnifeDebug() {

        ButterKnife.setDebug(BuildConfig.DEBUG);
    }
}
