package com.parianom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.parianom.R;
import com.parianom.adapter.RiwayatRVAdapter;
import com.parianom.api.BaseApiService;
import com.parianom.api.UtilsApi;
import com.parianom.model.PenjualanModel;
import com.parianom.model.PesananModel;
import com.parianom.model.PesananResponseModel;
import com.parianom.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatFragment extends Fragment {
    View v;
    private RecyclerView rv;
    private List<PesananModel> listRiwayat;
    ShimmerFrameLayout shimmer;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_riwayat, container, false);
        sessionManager = new SessionManager(getContext());
        rv = (RecyclerView) v.findViewById(R.id.riwayatRv);
        shimmer = (ShimmerFrameLayout) v.findViewById(R.id.shimmerRiwayat);
        getData();

        return v;
    }

    private void getData() {
        HashMap<String, String> user = sessionManager.getUserDetails();
        BaseApiService mApiService = UtilsApi.getApiService();
        Call<PesananResponseModel> get = mApiService.getPesananUser(Integer.parseInt(user.get(SessionManager.kunci_id_user)));
        get.enqueue(new Callback<PesananResponseModel>() {
            @Override
            public void onResponse(Call<PesananResponseModel> call, Response<PesananResponseModel> response) {
                listRiwayat = response.body().getData();
                RiwayatRVAdapter adapter = new RiwayatRVAdapter(getContext(), listRiwayat);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(adapter);
                rv.setVisibility(View.VISIBLE);
                shimmer.stopShimmer();
                shimmer.hideShimmer();
                shimmer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PesananResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}