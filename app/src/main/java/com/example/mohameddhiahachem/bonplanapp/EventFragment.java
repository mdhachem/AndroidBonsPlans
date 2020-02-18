package com.example.mohameddhiahachem.bonplanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohameddhiahachem.bonplanapp.Adapter.EventAdapter;
import com.example.mohameddhiahachem.bonplanapp.Entity.EventPlan;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventFragment extends Fragment {

    private int page = 0;
    private boolean isLoad = true;
    ArrayList<EventPlan> lists = new ArrayList<>();
    EventPlan[] events;
    private String search = "";
    EventAdapter adapter;
    private ListView listEvent;
    private ImageView searchBtn;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        listEvent = (ListView) view.findViewById(R.id.list_events);
        searchBtn = (ImageView) view.findViewById(R.id.btn_search_event);
        searchEditText = (EditText) view.findViewById(R.id.search_event);
        loadData();

        listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.event_sheet_id);
                //Toast.makeText(getContext(), "Product Id: " + textView.getText() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), EventSheet.class);
                intent.putExtra("ID", textView.getText().toString());
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = searchEditText.getText().toString();
                isLoad = true;
                page = 0;
                lists.clear();
                loadData();
            }
        });

        listEvent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listEvent.getLastVisiblePosition() - listEvent.getHeaderViewsCount() -
                        listEvent.getFooterViewsCount()) >= (listEvent.getCount() - 1)) {

                    page+= 5;
                    loadData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        return view;
    }


    public void loadData(){
        String URL_REGIST = "http://10.0.3.2:8000/api/events";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        Gson gson = new Gson();
                        events = gson.fromJson(jsonObject.getJSONArray("events").toString(), EventPlan[].class);
                        //Log.d("Plans", plans.toString());
                        for( EventPlan e : events){
                            lists.add(new EventPlan(e.getId(), e.getName(),e.getDateEvent(),e.getDescription(),e.getImage(),e.getAddress(),e.getCity(),e.getGovernorate()));
                        }

                        if(isLoad){
                            adapter = new EventAdapter(getContext(), lists);
                            listEvent.setAdapter(adapter);
                            isLoad = false;

                        }else{
                            adapter.notifyDataSetChanged();
                        }


                    }else{
                        Toast.makeText(getContext(), "not good", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), "Erorr Eroor !!!!!!!!!!!!!!!!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Erorr Eroor !!!!!!!!!!!!!!!!!!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("page", String.valueOf(page));
                params.put("search", search);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                2,
                1f));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
