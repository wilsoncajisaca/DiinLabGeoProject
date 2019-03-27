package com.geoinformatica.wilsoncajisaca.diinlab.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.contactos;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.contactosChips;
import com.geoinformatica.wilsoncajisaca.diinlab.R;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ContactosChipsHolder> {

    List<contactosChips> contacosChipsList;
    OnItemClickListener mItemClickListener;

    public MyRecyclerAdapter(List<contactosChips> contacosChips) {
        this.contacosChipsList = contacosChips;
    }


    @NonNull
    @Override
    public MyRecyclerAdapter.ContactosChipsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item,viewGroup,false);
        return new MyRecyclerAdapter.ContactosChipsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ContactosChipsHolder Holder, int i) {

        Holder.personName.setText(contacosChipsList.get(i).getName());
        Holder.person_number.setText(contacosChipsList.get(i).getPhone());
        Holder.cbSelected.setChecked(contacosChipsList.get(i).isSelected());

    }

    public void setItemSelected(int position, boolean isSelected) {
        if (position != -1) {
            contacosChipsList.get(position).setSelected(isSelected);
            notifyDataSetChanged();
        }
    }

    List<contactosChips> getContacosChipsList(){
        return contacosChipsList;
    }

    @Override
    public int getItemCount() {
        return contacosChipsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setPeople(List<contactosChips> contacosChipsList) {
        this.contacosChipsList = contacosChipsList;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public class ContactosChipsHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        LinearLayout cv;
        TextView personName,person_number;
        CheckBox cbSelected;

        public ContactosChipsHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = itemView.findViewById(R.id.cv);
            personName = itemView.findViewById(R.id.person_name);
            person_number=itemView.findViewById(R.id.person_number);
            cbSelected = itemView.findViewById(R.id.cbSelected);
        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }

        }
    }
    public void updateList(List<contactosChips> newList){
        contacosChipsList=new ArrayList<>();
        contacosChipsList.addAll(newList);
        notifyDataSetChanged();
    }
}

