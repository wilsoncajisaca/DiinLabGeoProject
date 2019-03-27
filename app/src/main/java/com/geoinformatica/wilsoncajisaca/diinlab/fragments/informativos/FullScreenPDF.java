package com.geoinformatica.wilsoncajisaca.diinlab.fragments.informativos;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class FullScreenPDF extends DialogFragment {

    public static String TAG = "FullScreenDialog";
    private ImageView atras;
    private File file;
    private PDFView pdfView;
    boolean EXIT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        Bundle b = getArguments();
        String ruta = b.getString("pdf", "");
        EXIT=b.getBoolean("exit",false);
        //and get whatever you have sent
        file= new File(ruta);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pdf_view_full_screen, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        atras=view.findViewById(R.id.atras);
        pdfView=view.findViewById(R.id.pdfView);

        atras.setOnClickListener(v -> {
            if(EXIT) getActivity().finish();
            else dismiss();
        });

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

}
