package es.udc.psi.agendaly.TimeTable;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.udc.psi.agendaly.R;

public class ItemDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView nombre = findViewById(R.id.textViewNombre);
        TextView horario = findViewById(R.id.textViewHorario);
        TextView clase = findViewById(R.id.textViewClase);
        Asignatura asignatura = getIntent().getParcelableExtra("item");

        nombre.setText(asignatura.getNombre());
        horario.setText(asignatura.getInicio() + "-" + asignatura.getFin());
        clase.setText(asignatura.getAula());

    }
}

