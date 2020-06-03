package com.mahmoud.mycarsdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import com.mahmoud.mycarsdata.adapter.CarAdapter;
import com.mahmoud.mycarsdata.adapter.EndlessRecyclerViewScrollListener;
import com.mahmoud.mycarsdata.apis.ApiClient;
import com.mahmoud.mycarsdata.apis.ApiService;
import com.mahmoud.mycarsdata.model.CarData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mahmoud.mycarsdata.ProgressDialogHelper.removeSimpleProgressDialog;
import static com.mahmoud.mycarsdata.ProgressDialogHelper.showSimpleProgressDialog;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerCars;
    CarAdapter carAdapter;
    ArrayList<CarData.Datum> datumArrayList;
    LinearLayoutManager linearLayoutManager;

    TextView textEmpty;


    int orderPage = 1;


    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerCars = findViewById(R.id.recycler_cars);
        textEmpty = findViewById(R.id.text_empty);
        swipeContainer = findViewById(R.id.swipe_container);

        //initialize views
        recyclerCars.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerCars.setLayoutManager(linearLayoutManager);
        datumArrayList = new ArrayList<>();
        carAdapter = new CarAdapter(datumArrayList);


        recyclerCars.setAdapter(carAdapter);

        getCarsByPage(orderPage);

        recyclerCars.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                orderPage++;
                getCarsByPage(orderPage);

            }
        });


        swipeContainer.setRefreshing(false);
        swipeContainer.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);


        swipeContainer.setOnRefreshListener(() -> {

            datumArrayList.clear();
            orderPage = 1;
            getCarsByPage(orderPage);

        });

    }

    private CarData productsResponse;

    public void getCarsByPage(int pageId) {

        productsResponse = null;
        showSimpleProgressDialog(this, false);


        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);

        Call<CarData> call = apiInterface.getCars(pageId);

        call.enqueue(new Callback<CarData>() {
            @Override
            public void onResponse(Call<CarData> call, Response<CarData> response) {

                swipeContainer.setRefreshing(false);
                removeSimpleProgressDialog();

                if (response.body() != null) {
                    productsResponse = response.body();


                    if (productsResponse.getStatus() == 1) {
                        if (!productsResponse.getData().isEmpty()) {


                            datumArrayList.addAll(productsResponse.getData());
                            carAdapter.notifyDataSetChanged();
                            recyclerCars.setVisibility(View.VISIBLE);
                            textEmpty.setVisibility(View.GONE);

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<CarData> call, Throwable t) {

                removeSimpleProgressDialog();
                swipeContainer.setRefreshing(false);


                Log.e("fail", "is : " + call.toString());
                t.printStackTrace();


            }
        });
    }


}