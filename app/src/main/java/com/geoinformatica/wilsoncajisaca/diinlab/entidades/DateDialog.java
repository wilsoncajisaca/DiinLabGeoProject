package com.geoinformatica.wilsoncajisaca.diinlab.entidades;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DateDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Obtener fecha actual
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Retornar en nueva instancia del dialogo selector de fecha
        return new DatePickerDialog(
                getActivity(),
                (DatePickerDialog.OnDateSetListener) getActivity(),
                year,
                month,
                day);
    }
}
