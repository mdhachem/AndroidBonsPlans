package com.example.mohameddhiahachem.bonplanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.mohameddhiahachem.bonplanapp.Adapter.PlanHomeAdapter;
import com.example.mohameddhiahachem.bonplanapp.Entity.Plan;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    Plan[] plans;
    private ListView listPlan;
    private int page = 0;
    ArrayList<Plan> lists = new ArrayList<>();
    private boolean isLoad = true;
    private String search = "";
    PlanHomeAdapter adapter;
    private Button  catshop, cathotels, catrestau;
    private ImageView searchBtn;
    private EditText searchEditText;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        listPlan = (ListView) view.findViewById(R.id.list_plans);
        searchBtn = (ImageView) view.findViewById(R.id.search_btn);
        searchEditText = (EditText) view.findViewById(R.id.search_field);
        catshop = (Button) view.findViewById(R.id.category_shops);
        cathotels = (Button) view.findViewById(R.id.category_hotels);
        catrestau = (Button) view.findViewById(R.id.category_restaurants);
        try{
            loadData();
        }catch (Exception e){

        }



        catshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", catshop.getText().toString());
                categoryFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        cathotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", cathotels.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        catrestau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryFragment categoryFragment = new CategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("category", catrestau.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, categoryFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
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

        listPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.place_id);

                Toast.makeText(getContext(), "Plan Id: " + textView.getText() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), com.example.mohameddhiahachem.bonplanapp.Plan.class);
                intent.putExtra("id", textView.getText().toString());
                startActivity(intent);
            }
        });

        listPlan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listPlan.getLastVisiblePosition() - listPlan.getHeaderViewsCount() -
                        listPlan.getFooterViewsCount()) >= (listPlan.getCount() - 1)) {

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
        String URL_REGIST = "http://10.0.3.2:8000/api/plans";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("code");
                    if(success){
                        //Log.d("sucessssss", jsonObject.getJSONArray("plans").toString());
                        Gson gson = new Gson();
                        plans = gson.fromJson(jsonObject.getJSONArray("plans").toString(), Plan[].class);
                        //Log.d("Plans", plans.toString());
                        for( Plan plan : plans){
                            lists.add(new Plan(plan.getId(), plan.getName(),plan.getDescription(), plan.getAddress(),plan.getImage(), plan.getCity(),plan.getGovernorate()));
                        }

                        if(isLoad){
                            adapter = new PlanHomeAdapter(getContext(), lists);
                            listPlan.setAdapter(adapter);
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
