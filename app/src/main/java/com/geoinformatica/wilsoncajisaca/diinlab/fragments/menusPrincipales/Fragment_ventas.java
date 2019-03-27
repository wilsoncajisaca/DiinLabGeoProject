package com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.geoinformatica.wilsoncajisaca.diinlab.Common.fotosVentas;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class Fragment_ventas extends Fragment{

    final String URLfoto="http://68.183.136.223/Agenda/imagenes/";
    List<fotosVentas> fotosArray=new ArrayList<>();

    private StoriesProgressView storiesProgressView;
    private ImageView image;
    View reverse ;
    View skip;

    private int counter = 0;

    long pressTime = 0L;
    long limit = 500L;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_ventas, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("Ventas");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        storiesProgressView = v.findViewById(R.id.stories);
        image = v.findViewById(R.id.image);
        reverse = v.findViewById(R.id.reverse);
        skip = v.findViewById(R.id.skip);

        imagenesDescargar();

        // bind reverse view

        reverse.setOnClickListener(v1 -> storiesProgressView.reverse());
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view

        skip.setOnClickListener(v1 -> storiesProgressView.skip());
        skip.setOnTouchListener(onTouchListener);

        return v;
    }

    private void imagenesDescargar(){
        SolicitudesJson json =new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject json) {
                try {
                    String resultado = json.getString("principal");

                    if (resultado.equals("CC")) {
                        String eventoslista=json.getString("datos");
                        JSONArray jsonArray=new JSONArray(eventoslista);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject js = jsonArray.getJSONObject(i);

                            agregarFoto(js.getString("photoid"));

                        }

                            storiesProgressView.setStoriesCount(jsonArray.length());

                            Picasso.get().load(URLfoto+fotosArray.get(0).getIdFoto()).into(image, new Callback() {
                                @Override
                                public void onSuccess() {

                                    storiesProgressView.setStoryDuration(9000L);
                                    storiesProgressView.startStories();

                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });

                            storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                                @Override
                                public void onNext() {
                                    if(counter<fotosArray.size()){
                                        counter++;
                                        Picasso.get().load(URLfoto+fotosArray.get(counter).getIdFoto()).into(image);
                                    }
                                }

                                @Override
                                public void onPrev() {
                                    if(counter > 0){
                                        counter--;
                                        Picasso.get().load(URLfoto+fotosArray.get(counter).getIdFoto()).into(image);
                                    }
                                }

                                @Override
                                public void onComplete() {
                                    counter=0;
                                    Picasso.get().load(URLfoto+fotosArray.get(counter).getIdFoto()).into(image, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                            storiesProgressView.setStoryDuration(5500L);
                                            storiesProgressView.startStories();

                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });

                                }
                            });

                    }else {
                        //todo el codigo
                        Toast.makeText(getContext(), "No se encontraron imagenes...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "No se pudo descargar las imagenes...", Toast.LENGTH_SHORT).show();
            }
        };

        json.solicitudJsonGET(getActivity(),SolicitudesJson.URL_GET_ALL_PHOTOS);

    }

    private void agregarFoto(String fotoId) {

        final fotosVentas fotos =new fotosVentas();
        fotos.setIdFoto(fotoId);
        fotosArray.add(fotos);

    }

    @Override
    public void onDestroy() {
        // Very important !
        super.onDestroy();
        storiesProgressView.destroy();
    }
}
