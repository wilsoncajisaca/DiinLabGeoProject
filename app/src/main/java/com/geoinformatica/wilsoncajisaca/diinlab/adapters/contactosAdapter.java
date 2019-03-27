package com.geoinformatica.wilsoncajisaca.diinlab.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.geoinformatica.wilsoncajisaca.diinlab.ui.ActividadesActivity;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.contactos;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class contactosAdapter extends RecyclerView.Adapter<contactosAdapter.HolderContactos>
        implements FastScrollRecyclerView.SectionedAdapter {

    public List<contactos> contactoList;
    contactosEmergent contactosEmergent;
    Context context;

    public contactosAdapter(List<contactos> contactoList, Context context) {
        this.contactoList = contactoList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderContactos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacto, viewGroup, false);
        contactosEmergent=new contactosEmergent(context);
        return new contactosAdapter.HolderContactos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderContactos holder, final int i) {

        String nombre = contactoList.get(i).getNombre().toUpperCase();
        ColorGenerator generator = ColorGenerator.MATERIAL;
//      holder.nombre.setTextColor(generator.getRandomColor());
        Drawable round_drawable = TextDrawable.builder().buildRound("" + nombre.charAt(0), generator.getColor("#d4d4d4"));
        holder.foto.setImageDrawable(round_drawable);

        holder.info.setOnClickListener(v ->
                contactosEmergent.perfilpersona(contactoList.get(i).getNombre(), contactoList.get(i).getTelefono(), contactoList.get(i).getCorreo()));

        holder.agenda.setOnClickListener(v -> {
            //todo code
            Intent intent = new Intent(context, ActividadesActivity.class);
            intent.putExtra("nombre", contactoList.get(i).getNombre().toUpperCase());
            intent.putExtra("id", contactoList.get(i).getIdContacto());
            context.startActivity(intent);
        });

        final String mobile = contactoList.get(i).getTelefono().replace(" ", "+");
        holder.phone.setText(mobile);
        holder.nombre.setText(contactoList.get(i).getNombre());
    }

    @Override
    public int getItemCount() {
        return contactoList.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return String.valueOf(contactoList.get(position).getNombre().charAt(0));
    }

    public class HolderContactos extends RecyclerView.ViewHolder {

        LinearLayout cardView;
        TextView nombre,phone;
        ImageView foto,info,agenda;

        public HolderContactos(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.llcontacto);
            nombre = itemView.findViewById(R.id.nombre);
            foto=itemView.findViewById(R.id.foto);
            info=itemView.findViewById(R.id.informacion);
            agenda=itemView.findViewById(R.id.agenda);
            phone=itemView.findViewById(R.id.phone);
        }
    }

    public void updateList(List<contactos> newList){
        contactoList=new ArrayList<>();
        contactoList.addAll(newList);
        notifyDataSetChanged();
    }
}