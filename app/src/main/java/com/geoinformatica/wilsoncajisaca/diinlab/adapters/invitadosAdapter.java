package com.geoinformatica.wilsoncajisaca.diinlab.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.invitados;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;

import java.util.List;


public class invitadosAdapter extends RecyclerView.Adapter<invitadosAdapter.HolderInvitados>{

    private List<invitados> invitadosList;
    private Context context;
    contactosEmergent contactosEmergent;

    public invitadosAdapter(List<invitados> invitadosList, Context context){
        this.invitadosList=invitadosList;
        this.context=context;
    }

    @NonNull
    @Override
    public invitadosAdapter.HolderInvitados onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_invited,viewGroup,false);
        contactosEmergent=new contactosEmergent(context);
        return new invitadosAdapter.HolderInvitados(v);
    }

    @Override
    public void onBindViewHolder(@NonNull invitadosAdapter.HolderInvitados holder, final int i) {

        holder.user_invited.setText(invitadosList.get(i).getNombre());

        final String nombre =invitadosList.get(i).getNombre();
        final String telefono=invitadosList.get(i).getCelular();
        final String email=invitadosList.get(i).getEmail();

        //Click para abrir perfil
        holder.RVinvited.setOnClickListener(v ->  contactosEmergent.perfilpersona(nombre,telefono,email));

    }

    @Override
    public int getItemCount() {
        return invitadosList.size();
    }

    public class HolderInvitados extends RecyclerView.ViewHolder {

        TextView user_invited;
        RelativeLayout RVinvited;

        public HolderInvitados(@NonNull View itemView) {
            super(itemView);

            user_invited=itemView.findViewById(R.id.invited_user);
            RVinvited=itemView.findViewById(R.id.RVinvitado);

        }
    }
}
