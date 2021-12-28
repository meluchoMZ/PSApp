/*
package es.udc.psi.agendaly.TimeTable.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import es.udc.psi.agendaly.MainActivity;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;

public class Service extends IntentService {
    AsignaturaPresenter mPresenter;

    String strHorario = "notificacion.horario";
    public Service() {
        super("IntentServiceNew");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;

        if(intent.getAction().equals(strHorario)) {
            contar(intent.getIntExtra(MainActivity.KEY_COUNT, 20));
        }

    }
    public void cancelCount(){

    }

    public void cancelRandom(){
        rndTask.cancel(true);
        rndTask = new RandBack();
    }

    public void contar(int count){
        myCountTask.doInBackground(count);

    }

    public void generate(){
        rndTask.doInBackground();
    }

    public boolean isAlive(){
        return myCountTask.getStatus() == AsyncTask.Status.PENDING;
    }

    // Binder given to clients
    private final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        Service getService() {
            // Return this instance of LocalService so clients can call public methods
            return Service.this;
        }
    }




    //AsyncTask para contar
    private class Background extends AsyncTask<Integer, Integer, String>{
        @Override
        protected String doInBackground(Integer... integers) {
            for (int c: integers) {
                for (int i = c; i >= 0 && !isCancelled(); i--) {
                    publishProgress(i);
                    Log.d("_TAGMS", "Cuenta Final  " + c + " cuenta actual " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace(); Thread.currentThread().interrupt();
                    }
                    if (isCancelled()) break;

                    Intent intent2 = new Intent();
                    intent2.setAction(strHorario);
                    intent2.putExtra("cuenta",String.valueOf(i));
                    sendBroadcast(intent2);
                }
                if(!isCancelled()) {
                    Intent intent2 = new Intent();
                    intent2.setAction(sw_off);
                    sendBroadcast(intent2);
                }

            }
            return "Done";
        }

    }


}


*/
