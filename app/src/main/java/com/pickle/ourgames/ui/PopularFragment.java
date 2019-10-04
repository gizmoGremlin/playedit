package com.pickle.ourgames.ui;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.pickle.ourgames.R;
import com.pickle.ourgames.ui.adapters.PopularAdapter;
import com.pickle.ourgames.model.Covers;
import com.pickle.ourgames.storage.SharedPref;
import com.pickle.ourgames.ui.viewmodels.PopularViewModel;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PopularFragment extends Fragment  {

    private PopularViewModel viewModel;
    RecyclerView rv;
    PopularAdapter adapter;
    PopularAdapter.OnItemClickListener listener;
    GridLayoutManager layoutManager;
    FirebaseAuth auth;



    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

            View v = inflater.inflate(R.layout.popular_fragment, container, false);
            rv = v.findViewById(R.id.rv_popular);



            return v;
        }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewModel = ViewModelProviders.of(this).get(PopularViewModel.class);

        String text = "fields *,popularity; sort popularity desc;";
        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        viewModel.loadCoversById(body);

        listener = new PopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Covers item) {

                SharedPref.write(SharedPref.GAME_NAME, item.getGame() );



                FragmentTransaction trans = getFragmentManager().beginTransaction();

                trans.replace(R.id.containerView, new PopularDetailFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();


            }
        };
        viewModel.getCoversLiveData().observe(getViewLifecycleOwner(), items ->{

            layoutManager = new GridLayoutManager(getContext(),3);

            adapter = new PopularAdapter(items, listener);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);



        });

    }

    public  boolean isLoggedIn(){
        if(auth.getCurrentUser() == null){
            return false;
        }else{
            return true;
        }

    }


}
