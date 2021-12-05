package es.udc.psi.agendaly.TimeTable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.viewmodel.AsignaturaViewModel;

public class AsignaturaAdapter extends RecyclerView.Adapter<AsignaturaAdapter.MyViewHolder> {

    private List<AsignaturaViewModel> mItems;

    public AsignaturaAdapter() {

        mItems = new ArrayList<>();
    }

    public void setItems(List<AsignaturaViewModel> items){
        mItems=items;
        notifyDataSetChanged();
    }

    public void updateItem(AsignaturaViewModel item,
                           int position) {

        mItems.add(position, item);
        notifyItemChanged(position);
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
        holder.bind(mItems.get(position));
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView nombre,horario,clase;
        public MyViewHolder(View view) {
            super(view);
            nombre = view.findViewById(R.id.textViewNombre);
            horario = view.findViewById(R.id.textViewHorario);
            clase = view.findViewById(R.id.textViewClase);
        }
        public void bind(AsignaturaViewModel asignatura) {
            nombre.setText(asignatura.getName());
            horario.setText(asignatura.getHora());
            clase.setText(asignatura.getAula());
        }


    }


}