package es.udc.psi.agendaly.TimeTable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class DeleteEvent extends BaseActivity implements AsignaturaView {
    //Asignatura asignatura = new Asignatura();
    private EventAdapter mAdapter;
    AsignaturaPresenter mPresenter;

    @BindView(R.id.butDelete)
    Button butDelete;

    @BindView(R.id.editTextAsignaturaDel)
    EditText editTextAsignatura;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrar_clases);
        setUI();
        mPresenter = new AsignaturaDatabaseImp(this,getBaseContext());
         builder = new AlertDialog.Builder(this);

    }

    public void setUI(){
        nameAsignatura();
        borrarAsignatura();
    }



    public void borrarAsignatura(){
        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(getString(R.string.delete_question))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String a = nameAsignatura();
                                if(a != null && !a.equals("")) {
                                    mPresenter.deleteClass(a);
                                    Toast.makeText(getBaseContext(), getString(R.string.asignatura_borrada), Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(getBaseContext(), getString(R.string.asignatura_noencontrada), Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }

    public void clearAll(){
        editTextAsignatura.setText("");

    }

    public String nameAsignatura(){
        String eT = editTextAsignatura.getText().toString();
        if(!eT.isEmpty()) {return eT;}
        else {return "";}
    }


    @Override
    public void showAsignaturas(List<AsignaturaViewModel> asignaturas) {

        mAdapter.setItems(asignaturas);
    }

    @Override
    public void sendNotification(List<AsignaturaViewModel> asignaturas) {

    }

}



