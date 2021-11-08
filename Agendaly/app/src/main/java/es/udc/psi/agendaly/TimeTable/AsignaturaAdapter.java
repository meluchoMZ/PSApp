package es.udc.psi.agendaly.TimeTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.udc.psi.agendaly.R;

public class AsignaturaAdapter extends RecyclerView.Adapter<AsignaturaAdapter.MyViewHolder> {

    private final ArrayList<Asignatura> mDataset;

    public AsignaturaAdapter(ArrayList<Asignatura> myDataset) {
        mDataset = myDataset;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView nombre,horario,clase;
        public MyViewHolder(View view) {
            super(view);
            nombre = view.findViewById(R.id.textViewNombre);
            horario = view.findViewById(R.id.textViewHorario);
            clase = view.findViewById(R.id.textViewClase);
        }
        public void bind(Asignatura Asignatura) {
            nombre.setText(Asignatura.getNombre());
            horario.setText(Asignatura.getHorario());
            clase.setText(Asignatura.getClase());
        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_detail, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mDataset.get(position));
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}