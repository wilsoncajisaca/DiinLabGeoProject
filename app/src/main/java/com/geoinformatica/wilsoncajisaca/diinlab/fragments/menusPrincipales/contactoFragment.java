package com.geoinformatica.wilsoncajisaca.diinlab.fragments.menusPrincipales;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.Contactos__;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.StickyHeaderCommon;
import com.geoinformatica.wilsoncajisaca.diinlab.R;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.SolicitudesJson;
import com.geoinformatica.wilsoncajisaca.diinlab.internet.VolleyRP;
import com.geoinformatica.wilsoncajisaca.diinlab.adapters.contactosAdapter;
import com.geoinformatica.wilsoncajisaca.diinlab.Common.contactos;
import com.geoinformatica.wilsoncajisaca.diinlab.entidades.preferences;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.SimpleDividerItemDecoration;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.contactosEmergent;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.swipe_recyclerView.SwipeController;
import com.geoinformatica.wilsoncajisaca.diinlab.utils.swipe_recyclerView.SwipeControllerActions;
import com.google.gson.Gson;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class contactoFragment extends Fragment
        implements android.widget.SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    private List<contactos> contactosList;//Conecta al adaptador
    List<contactos> newList;
    ArrayList<Contactos__> listaC;
    private contactosAdapter adapter;
    FastScrollRecyclerView rvA;
    LinearLayout error_net,empty_contact;
    ShimmerLayout shimmer;
    boolean search=false;
    private VolleyRP volley;
    private RequestQueue request;
    contactosEmergent contactosEmergent;
    SwipeController swipeController = null;
    LinearLayoutManager lmA;

    public static final String TAG = "ContactoFragment";
    private FragmentIterationListener mCallback = null;
    public interface FragmentIterationListener{
        public void onFragmentIteration(Bundle parameters);
    }

    Context mContext;

    final private int PICK_CONTACT_REQUEST=122;

    public static contactoFragment newInstance(Bundle arguments){
        contactoFragment f = new contactoFragment();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactosfragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().setTitle("Clientes");

        contactosEmergent=new contactosEmergent(getContext());

        shimmer=v.findViewById(R.id.shimmer);
        error_net=v.findViewById(R.id.error_net);
        empty_contact=v.findViewById(R.id.Empty);
        rvA=v.findViewById(R.id.recycler);
        listaC=new ArrayList<>();

        volley = VolleyRP.getInstance(getContext());
        request=volley.getRequestQueue();

        if (preferences.obtenerPreferenceBooleanContact(getContext(),preferences.PREFERENCE_ESTADO_SYNC))
            syncContact();

        lmA = new LinearLayoutManager(getContext());
        rvA.setLayoutManager(lmA);

        rvA.setItemAnimator(new DefaultItemAnimator());

        contactosList=new ArrayList<>();

        adapter=new contactosAdapter(contactosList,getContext());
        rvA.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        rvA.setAdapter(adapter);


        swipeController=new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

                    if (search==false){
                        contactosEmergent.whatsapp(contactosList.get(position).getTelefono());
                    }else {
                        contactosEmergent.whatsapp(newList.get(position).getTelefono());
                    }

                adapter.notifyDataSetChanged();

            }
            @Override
            public void onLeftClicked(int position) {

                    if(search==false) {
                        contactosEmergent.llamar(contactosList.get(position).getTelefono(),
                                contactosList.get(position).getNombre());

                    }else {

                        contactosEmergent.llamar(newList.get(position).getTelefono(),
                                newList.get(position).getNombre());
                    }

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvA);

        rvA.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


        all_contacts();

        return v;
    }

    private void recargarFragment(){
        // Reload current fragment
        Fragment frg=null;
        frg = getFragmentManager().findFragmentByTag("1");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    public void syncContact(){
        new RxPermissions(this)
                .request(Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        Log.i("Ok","Se otorgo los permisos de lectura de datos y llamadas");
                        sincronizarcontactos();
                    } else {
                        Toast.makeText(getContext(), "No se otorgo los permisos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sincronizarcontactos(){

        String user_login=preferences.obtenerPreferenceString(getActivity(),preferences.USER_PREFERENCE_LOGIN);

        // Se tiene permiso
        final String[] projeccion = new String[]{Phone._ID,ContactsContract.Data.DISPLAY_NAME,
                Phone.NUMBER, Phone.NORMALIZED_NUMBER};

        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                Phone.CONTENT_ITEM_TYPE + "' AND "
                + Phone.NORMALIZED_NUMBER + " IS NOT NULL";

        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        final Cursor c = getActivity().getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                projeccion,
                selectionClause,
                null,
                sortOrder);

        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
        int indexOfNormalizedNumber = c.getColumnIndex(Phone.NORMALIZED_NUMBER);

        while(c.moveToNext()){
            String email=c.getString(1).replace(" ","_");
            String normalizedNumber = c.getString(indexOfNormalizedNumber);
            if(normalizedNumbersAlreadyFound.add(normalizedNumber)){
                if(!TextUtils.isEmpty(c.getString(1))){
                    Contactos__ contactosAtributos = new Contactos__(c.getString(1), "Paute", email, c.getString(3), c.getString(3), user_login);
                    listaC.add(contactosAtributos);
                }
            }
        }
        c.close();

        Gson gson=new Gson();
        String jsonOutput = gson.toJson(listaC);

        String url="http://68.183.136.223/Agenda/nuevo.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                response ->  {
                    all_contacts();
                    preferences.savePreferenceBooleanContact(getContext(),false,preferences.PREFERENCE_ESTADO_SYNC);
                    Log.d("Ok","Se han sicronizado sus contactos...");
                },
                error ->  {
                    Toast.makeText(mContext, "Parece que tenemos problemas de conexion", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                    error.getMessage(); // when error come i will log it
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> param=new HashMap<String, String>();
                param.put("array",jsonOutput); // array is key which we will use on server side
                return param;
            }
        };
        VolleyRP.addToQueue(stringRequest,request,getContext(),volley);
    }

    public void all_contacts(){

        contactosList.clear();

        SolicitudesJson sj = new SolicitudesJson() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String resultado=j.getString("principal");
                    if (resultado.equals("CC")){
                        try {
                            shimmer.setVisibility(View.GONE);
                            rvA.setVisibility(View.VISIBLE);
                            error_net.setVisibility(View.GONE);
                            empty_contact.setVisibility(View.GONE);

                            String contactos=j.getString("datos");
                            JSONArray jsonArray=new JSONArray(contactos);

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject js =jsonArray.getJSONObject(i);
                                agregarcontacto(js.getString("id"),
                                        js.getString("name"),
                                        js.getString("mobile"),
                                        js.getString("email"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        shimmer.setVisibility(View.GONE);
                        rvA.setVisibility(View.GONE);
                        error_net.setVisibility(View.GONE);
                        empty_contact.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "Ocurrio un error al momento de obtener los contactos", Toast.LENGTH_SHORT).show();
                shimmer.setVisibility(View.GONE);
                rvA.setVisibility(View.GONE);
                error_net.setVisibility(View.VISIBLE);
                empty_contact.setVisibility(View.GONE);
            }
        };
        String usuario = preferences.obtenerPreferenceString(getActivity(),preferences.USER_PREFERENCE_LOGIN);
        sj.solicitudJsonGET(getActivity(),SolicitudesJson.URL_GET_CONTACTOS_BY_ID+usuario);
    }

    public void agregarcontacto(String id, final String nombre, String telefono, String correo){
        final contactos contactosatributos =new contactos();
        contactosatributos.setIdContacto(id);
        contactosatributos.setNombre(nombre);
        contactosatributos.setTelefono(telefono);
        contactosatributos.setCorreo(correo);
        contactosList.add(contactosatributos);
        adapter.notifyDataSetChanged();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar contacto");


        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sincronizar:
                //todo code

                syncContact();

                break;
            case R.id.agregar:
                //todo code

                Intent i = new Intent(Intent.ACTION_INSERT);
                i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                if (Integer.valueOf(Build.VERSION.SDK) > 14)
                    i.putExtra("finishActivityOnSaveCompleted", true); // Fix for 4.0.3 +
                startActivityForResult(i, PICK_CONTACT_REQUEST);

                break;

        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        search=true;
        String userInput=newText.toLowerCase();
        newList=new ArrayList<>();

        for(contactos contactos1 : contactosList){
            if (contactos1.getNombre().toLowerCase().contains(userInput)||contactos1.getTelefono().contains(userInput))
            {
                newList.add(contactos1);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    public void resetSearch() {

        search=false;
        adapter=new contactosAdapter(contactosList,getContext());
        rvA.setAdapter(adapter);

    }

    //El fragment se ha adjuntado al Activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //La vista de layout ha sido creada y ya está disponible
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //La vista ha sido creada y cualquier configuración guardada está cargada
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //El Activity que contiene el Fragment ha terminado su creación
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}