package com.parianom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.PesanRVAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.RoomModel;
import com.parianom.model.RoomResponseModel;
import com.parianom.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<RoomModel> roomModels;
    ShimmerFrameLayout shimmer;
    SessionManager sessionManager;
    LinearLayout empty;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_pesan, container, false);
        sessionManager = new SessionManager(getContext());
        rv = (RecyclerView) v.findViewById(R.id.pesanRv);


        shimmer = (ShimmerFrameLayout) v.findViewById(R.id.shimmerPesan);
        empty = v.findViewById(R.id.layout_empty_pesan);
        getRoom();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rv.setVisibility(View.VISIBLE);
//                shimmer.stopShimmer();
//                shimmer.hideShimmer();
//                shimmer.setVisibility(View.GONE);
//            }
//        }, 5000);

        return v;
    }

    private void getRoom() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<RoomResponseModel> get = mApiService.getRoom(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        get.enqueue(new Callback<RoomResponseModel>() {
            @Override
            public void onResponse(Call<RoomResponseModel> call, Response<RoomResponseModel> response) {
                if (response.body().getMessage().equals("exist")){
                    roomModels = response.body().getData();
                    PesanRVAdapter adapter = new PesanRVAdapter(getContext(), roomModels);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(adapter);
                    rv.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                }else{
                    empty.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.VISIBLE);
                    shimmer.stopShimmer();
                    shimmer.hideShimmer();
                    shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RoomResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}