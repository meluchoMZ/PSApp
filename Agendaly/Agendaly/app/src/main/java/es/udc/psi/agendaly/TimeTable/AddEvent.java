package es.udc.psi.agendaly.TimeTable;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaDatabaseImp;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaPresenter;
import es.udc.psi.agendaly.TimeTable.presenter.AsignaturaView;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class AddEvent extends BaseActivity implements AsignaturaView {
    Asignatura asignatura = new Asignatura();
    private EventAdapter mAdapter;
    AsignaturaPresenter mPresenter;

    @BindView(R.id.butInicio)
    Button butInicio;

    @BindView(R.id.butFin)
    Button butFin;

    @BindView(R.id.butSave)
    Button butSave;

    @BindView(R.id.editTextAsignatura)
    EditText editTextAsignatura;

    @BindView(R.id.editTextAula)
    EditText editTextAula;

    @BindView(R.id.spinner)
    Spinner spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_clases);
        setUI();
        mPresenter = new AsignaturaDatabaseImp(this,getBaseContext());


    }

    public void setUI(){
        addInicio();
        addFin();
        chooseDay();
        nameAsignatura();
        nameAula();
        guardarAsignatura();
        setSpinner();
    }



    public void guardarAsignatura(){
        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameAsignatura();
                nameAula();
                if(asignatura !=null && asignatura.getFin() != null && asignatura.getFin() != null
                        && asignatura.getAula() != null && asignatura.getNombre() != null) {
                    mPresenter.insert(asignatura);
                    Toast.makeText(getBaseContext(), getString(R.string.add_asignatura), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    public void clearAll(){
        editTextAsignatura.setText("");
        editTextAula.setText("");

    }

    public void nameAsignatura(){
        String eT = editTextAsignatura.getText().toString();
        if(!eT.isEmpty()) {asignatura.setNombre(eT);}
    }

    public void nameAula(){
        String eT = editTextAula.getText().toString();
        if(!eT.isEmpty()) {asignatura.setAula(eT);}
    }

    public void chooseDay(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void addInicio(){

        butInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showTimePickerDialog();
                final Calendar getDate = Calendar.getInstance();
                SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm a");
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        getDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        getDate.set(Calendar.MINUTE, minute);
                        String ini = timeformat.format(getDate.getTime());
                        asignatura.setInicio(ini);
                    }
                }, getDate.get(Calendar.HOUR_OF_DAY), getDate.get(Calendar.MINUTE), false);

                timePickerDialog.show();


            }
        });
    }

    public void addFin(){

        butFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showTimePickerDialog();
                final Calendar getDate = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        getDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        getDate.set(Calendar.MINUTE, minute);
                        SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm a");
                        String fin = timeformat.format(getDate.getTime());
                        asignatura.setFin(fin);
                    }
                }, getDate.get(Calendar.HOUR_OF_DAY), getDate.get(Calendar.MINUTE), false);

                timePickerDialog.show();

            }
        });
    }


    public void setSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String d = adapterView.getItemAtPosition(i).toString();
                if(d!=null){asignatura.setDay(d);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void showAsignaturas(List<AsignaturaViewModel> artists) {

        mAdapter.setItems(artists);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void updateAsignatura(AsignaturaViewModel asignatura, int position) {
        mAdapter.updateItem(asignatura,position);
    }
}

