package com.geoinformatica.wilsoncajisaca.diinlab.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfView extends AppCompatActivity {

    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView=findViewById(R.id.pdfView);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
            file=new File(bundle.getString("path",""));

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }

}
