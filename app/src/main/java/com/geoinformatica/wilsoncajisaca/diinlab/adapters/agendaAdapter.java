package com.geoinformatica.wilsoncajisaca.diinlab.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.agenda;
import com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales.calendar_view_event;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.EventDescription;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class agendaAdapter extends RecyclerView.Adapter<agendaAdapter.HolderAgenda>{

    private List<agenda> agendaList;
    private Context context;

    public agendaAdapter(List<agenda> agendaList, Context context){
        this.agendaList=agendaList;
        this.context=context;
    }

    @NonNull
    @Override
    public HolderAgenda onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_agenda,viewGroup,false);
        return new agendaAdapter.HolderAgenda(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HolderAgenda holder, final int i) {

//        Calendar calendarNow = new GregorianCalendar(TimeZone.getTimeZone("America/Guayaquil"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date;


        String Contenido = agendaList.get(i).getFecha();
        String[] arraycontenido = Contenido.split(" ");

        for (int o = 0; o < arraycontenido.length; o++) {

            String fecha = arraycontenido[0];

            try {

                date = formatter.parse(fecha);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                holder.mImageGenerator.setIconSize(60, 60);
                holder.mImageGenerator.setDateSize(20);
                holder.mImageGenerator.setMonthSize(10);

                holder.mImageGenerator.setDatePosition(47);
                holder.mImageGenerator.setMonthPosition(21);

                holder.mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
                holder.mImageGenerator.setMonthColor(Color.WHITE);

                holder.mImageGenerator.setStorageToSDCard(true);

                holder.mGeneratorDateIcon = holder.mImageGenerator.generateDateImage(calendar, R.drawable.calendar_empty);
                holder.mDisplayGenerateImage.setImageBitmap(holder.mGeneratorDateIcon);

                String titulo=agendaList.get(i).getTitulo();
                String descripcion=agendaList.get(i).getDescripcion();
                String lugar=agendaList.get(i).getUbicacion();
                String fechapar=agendaList.get(i).getFecha();

                holder.title.setText(titulo);
                holder.descripcion.setText(descripcion);
                holder.ubicacion.setText(lugar);
                holder.fecha.setText(fechapar);

                holder.evento.setOnClickListener(v -> {
                    Intent intent=new Intent(context,EventDescription.class);
                    intent.putExtra("id",agendaList.get(i).getId_event());
                    context.startActivity(intent);
                });

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    public static class HolderAgenda extends RecyclerView.ViewHolder {

        LinearLayout llcontacto,LLTitle;
        TextView descripcion,title,ubicacion,fecha;
        CardView evento;

        Bitmap mGeneratorDateIcon;
        ImageGenerator mImageGenerator;
        ImageView mDisplayGenerateImage;


        public HolderAgenda(View itemView) {
            super(itemView);
            llcontacto = itemView.findViewById(R.id.llcontacto);
            evento=itemView.findViewById(R.id.agenda);

            title = itemView.findViewById(R.id.title);
            descripcion = itemView.findViewById(R.id.descripcion);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            fecha = itemView.findViewById(R.id.fecha);
            LLTitle=itemView.findViewById(R.id.LLTitle);

            mImageGenerator=new ImageGenerator(itemView.getContext());
            mDisplayGenerateImage=itemView.findViewById(R.id.calendarioimage);
        }
    }

    public void updateList(List<agenda> newList){
        agendaList=new ArrayList<>();
        agendaList.addAll(newList);
        notifyDataSetChanged();
    }

}
