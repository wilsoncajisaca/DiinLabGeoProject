package com.geoinformatica.wilsoncajisaca.diinlab.fragments.informativos;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.dpizarro.autolabel.library.AutoLabelUI;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.BusAgenda;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.contactosChips;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.listInvitados;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.adapters.MyRecyclerAdapter;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.VerificationPackage;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitadosFragment extends Fragment implements android.widget.SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {

    private final String KEY_INSTANCE_STATE_PEOPLE = "statePeople";

    //Defino el label de nombres
    private AutoLabelUI mAutoLabel;
    //almaceno en array los contactos
    public List<contactosChips> contactosChipsList;
    //defino array para llenar a los invitados
    List<listInvitados> listInvitadoslist;

    private MyRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FrameLayout listaI;
    List<contactosChips> newList;
    boolean search=false;
    VerificationPackage verificationPackage;

    //Variables globales para recibir datos del evento...
    private String NombreE;
    private String DescripcionE;
    private String UbicacionE;
    private String fechaDesde;
    private String fechaHasta;
    private String horaDesde;
    private String horaHasta;
    private boolean alldayE;
    private Button guardar;

    private VolleyRP volley;
    private RequestQueue request;

    contactosEmergent contactosEmergent;

    //----------------------------------------------

    //Defino el metodo event bus extraendo las variables desde newActivity
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(BusAgenda event) {

        NombreE=
                event.tituloEvento;
        DescripcionE=
                event.descripcionevento;
        UbicacionE=
                event.direccionEvento;
        fechaDesde=
                event.fechaDesde;
        fechaHasta=
                event.fechaHasta;
        horaDesde =
                event.horaDesde;
        horaHasta =
                event.horaHasta;
        alldayE=
                event.alldayevento;

//        Toast.makeText(getContext(), "N "+NombreE+" D "+DescripcionE+" U "+UbicacionE+" F " +fechaDesde+ " FD "
//                +fechaDesde+" FH "+fechaHasta+" HD "+horaDesde+" HH "+horaHasta+" AD "+alldayE, Toast.LENGTH_SHORT).show();

        EventBus.getDefault().removeStickyEvent(event);

    }

    public static InvitadosFragment newInstance() {
        return new InvitadosFragment();
    }

    public InvitadosFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        mAutoLabel = view.findViewById(R.id.label_view);
        mAutoLabel.setBackgroundResource(R.drawable.round_corner_background);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progress_bar);
        listaI=view.findViewById(R.id.listaI);
        guardar=view.findViewById(R.id.gg);
        contactosEmergent=new contactosEmergent(getContext());

        verificationPackage=new VerificationPackage(getContext());

        volley = VolleyRP.getInstance(getContext());
        request=volley.getRequestQueue();
        listInvitadoslist=new ArrayList<>();
        setListeners();
        setRecyclerView();

        guardar.setOnClickListener(v -> enviarEvento());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            List<contactosChips> contactosChipsList = savedInstanceState.getParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE);
            if (contactosChipsList != null) {
                contactosChipsList = contactosChipsList;
                adapter.setPeople(contactosChipsList);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void itemListClicked(int position) {
            if(search==false)
                allcontact(position);
            else
                searchcontact(position);
    }

    private void cargainvitados(String id) {
        listInvitados invitado=new listInvitados(id);
        //add into list array
        listInvitadoslist.add(invitado);
        // now here we convert this list array into json string
    }

    private void setListeners() {

        mAutoLabel.setOnRemoveLabelListener((view, position) -> adapter.setItemSelected(position,false));

        mAutoLabel.setOnLabelsEmptyListener(() -> Snackbar.make(recyclerView,"Agrege al menos un invitado",Snackbar.LENGTH_LONG).show());

    }

    private void validarListInvitados(){

        if (listInvitadoslist == null||listInvitadoslist.size()==0){

            new MaterialStyledDialog.Builder(getContext())
                    .setTitle("Espera!!")
                    .setDescription("No seleccionaste ningun invitado, deseas continuar de todas formas?")
                    .setIcon(R.drawable.guest_list)
                    .setHeaderColor(R.color.color_event_people)
                    .withIconAnimation(false)
                    .withDialogAnimation(true)
                    .setPositiveText("C O N T I N U A R")
                    .onPositive((dialog, which) -> enviarEvento())
                    .setNegativeText("C A N C E L A R")
                    .onNegative((dialog, which) -> dialog.dismiss())
                    .show();
        }else {
            enviarEvento();
        }

    }

    private void enviarEvento() {

        guardar.setEnabled(false);

        Gson gson = new Gson();
        String datos = gson.toJson(listInvitadoslist);
        String ubicacion = UbicacionE.replace(" ", "%20");
        String nombreEve = NombreE.replace(" ", "%20");
        String descEve = DescripcionE.replace(" ", "%20");
        String user_login = preferences.obtenerPreferenceString(getActivity(), preferences.USER_PREFERENCE_LOGIN);

        if (alldayE == true) {
            String urlInsertEventAllDay = "http://68.183.136.223/Agenda/inserteventoAllday.php" +
                    "?title=" + nombreEve +
                    "&fechaI=" + fechaDesde +
                    "&user_id=" + user_login +
                    "&locate=" + ubicacion +
                    "&description=" + descEve +
                    "&fechaF=" + fechaHasta;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertEventAllDay,
                    response -> {
                        getActivity().finish();
                        Log.d("Ok", "Evento añadido...");
                        verificationPackage.addEventGoogleCalendar(alldayE,NombreE,DescripcionE,UbicacionE,fechaDesde,horaDesde,fechaHasta,horaHasta);
                        Toast.makeText(getContext(), "Genial se guardo tu evento...", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        guardar.setEnabled(true);
                        Toast.makeText(getContext(), "Parece que tenemos problemas de conexion", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new HashMap<>();
                    param.put("array", datos); // array is key which we will use on server side
                    return param;
                }
            };
            VolleyRP.addToQueue(stringRequest, request, getContext(), volley);

        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {

                Date horaD = format.parse(fechaDesde + " " + horaDesde);
                Date horaH = format.parse(fechaHasta + " " + horaHasta);

                String urlinsert = "http://68.183.136.223/Agenda/InsertEventoPost.php" +
                        "?title=" + nombreEve +
                        "&fechaI=" + fechaDesde +
                        "&horaI=" + horaDesde + ":00" +
                        "&user_id=" + user_login +
                        "&locate=" + ubicacion +
                        "&description=" + descEve +
                        "&fechaF=" + fechaHasta +
                        "&horaF=" + horaHasta + ":00" +
                        "&duracion=" + contactosEmergent.getDiferencia(horaD, horaH);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlinsert,
                        response -> {
                            getActivity().finish();
                            Log.d("Ok", "Evento añadido...");
                            verificationPackage.addEventGoogleCalendar(alldayE,NombreE,DescripcionE,UbicacionE,fechaDesde,horaDesde,fechaHasta,horaHasta);
                            Toast.makeText(getContext(), "Genial se guardo tu evento...", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                        },
                        error -> {
                            guardar.setEnabled(true);
                            Toast.makeText(getContext(), "Parece que tenemos problemas de conexion", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                            error.getMessage(); // when error come i will log it
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> param = new HashMap<>();
                        param.put("array", datos); // array is key which we will use on server side
                        return param;
                    }
                };
                VolleyRP.addToQueue(stringRequest, request, getContext(), volley);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        contactosChipsList = new ArrayList<>();

        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    listaI.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    getActivity().setTitle("Seleccione a los invitados");
                    String resultado=j.getString("principal");
                    if (resultado.equals("CC")){
                        try {

                            String contactos=j.getString("datos");
                            JSONArray jsonArray=new JSONArray(contactos);

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject js =jsonArray.getJSONObject(i);
                                agregarcontacto(js.getString("id"),
                                        js.getString("mobile"),
                                        js.getString("name"),
                                        false);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void solicitudErronea() {
                listaI.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Ocurrio un error al momento de obtener los contactos", Toast.LENGTH_SHORT).show();
            }
        };
        String usuario = preferences.obtenerPreferenceString(getActivity(),preferences.USER_PREFERENCE_LOGIN);
        sj.solicitudJsonGET(getActivity(),SolicitudesJson.URL_GET_CONTACTOS_BY_ID+usuario);

        adapter = new MyRecyclerAdapter(contactosChipsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> itemListClicked(position));

    }

    public void agregarcontacto(String id,String phone, final String nombre, boolean select){
        final contactosChips contactosatributos =new contactosChips();
        contactosatributos.setId(id);
        contactosatributos.setPhone(phone);
        contactosatributos.setName(nombre);
        contactosatributos.setSelected(select);
        contactosChipsList.add(contactosatributos);
        adapter.notifyDataSetChanged();
    }
    public void allcontact(int position){

        contactosChips person = contactosChipsList.get(position);
        boolean isSelected = person.isSelected();
        boolean success;
        if (isSelected) {

            success = mAutoLabel.removeLabel(position);

        } else {
            success = mAutoLabel.addLabel(person.getName(), position);
            cargainvitados(person.getId());
        }
        if (success) {
            adapter.setItemSelected(position, !isSelected);
        }
    }
    public void searchcontact(int position){

        contactosChips person = newList.get(position);
        boolean isSelected = person.isSelected();
        boolean success;
        if (isSelected) {

            success = mAutoLabel.removeLabel(position);

        } else {
            success = mAutoLabel.addLabel(person.getName(), position);
            cargainvitados(person.getId());
        }
        if (success) {
            adapter.setItemSelected(position, !isSelected);
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        search=true;
        String userInput=newText.toLowerCase();
        newList=new ArrayList<>();

        for(contactosChips contactosChips : contactosChipsList){
            if (contactosChips.getName().toLowerCase().contains(userInput))
            {
                newList.add(contactosChips);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    public void resetSearch() {

        search=false;
        adapter = new MyRecyclerAdapter(contactosChipsList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> itemListClicked(position));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.buscar_invitados, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar contacto");
    }


}

