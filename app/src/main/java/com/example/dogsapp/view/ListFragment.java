package com.example.dogsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dogsapp.R;
import com.example.dogsapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListFragment extends Fragment {

    private ListViewModel mListViewModel;
    private DogsListAdapter mDogsListAdapter = new DogsListAdapter(new ArrayList<>());

    @BindView(R.id.dogsList)
    RecyclerView dogsList;

    @BindView(R.id.listError)
    TextView listError;

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail();
//        Navigation.findNavController(view).navigate(action);

         mListViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
         mListViewModel.refresh();

         dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
         dogsList.setAdapter(mDogsListAdapter);

         mSwipeRefreshLayout.setOnRefreshListener(() -> {

             dogsList.setVisibility(View.GONE);
             listError.setVisibility(View.GONE);
             loadingView.setVisibility(View.VISIBLE);
             mListViewModel.refreshByCache();
             mSwipeRefreshLayout.setRefreshing(false);
         });

         observeViewModel();
    }


    public void observeViewModel(){

        mListViewModel.dogs.observe(this, dogs -> {
            if (dogs != null && dogs instanceof List){
                dogsList.setVisibility(View.VISIBLE);
                mDogsListAdapter.updateDogsList(dogs);
            }
        });

        mListViewModel.dogLoadError.observe(this, isError -> {
            if (isError != null && isError instanceof Boolean){
                listError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        mListViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null && isLoading instanceof Boolean){
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading){
                    listError.setVisibility(View.GONE);
                    dogsList.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionSettings:

                if (isAdded()){

                    Navigation.findNavController(getView()).navigate(ListFragmentDirections.actionListFragmentToActionSettings());
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
