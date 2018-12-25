package com.androidarchcomp.mvpexample.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidarchcomp.mvpexample.R;
import com.androidarchcomp.mvpexample.adapters.ResourceRecyclerViewAdapter;
import com.androidarchcomp.mvpexample.model.Resource;
import com.androidarchcomp.mvpexample.model.ResourceList;
import com.androidarchcomp.mvpexample.network.APIClient;
import com.androidarchcomp.mvpexample.network.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnResourceListFragmentInteractionListener}
 * interface.
 */
public class ResourceListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnResourceListFragmentInteractionListener mListener;
    private APIInterface apiInterface;
    private View mProgressView;
    private RecyclerView recyclerView;
    private ResourceRecyclerViewAdapter resourceRecyclerViewAdapter;
    int page = 0, totalPage = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ResourceListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ResourceListFragment newInstance(int columnCount) {
        ResourceListFragment fragment = new ResourceListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        mProgressView = (ProgressBar) view.findViewById(R.id.progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_list);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        resourceRecyclerViewAdapter = new ResourceRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(resourceRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResourceListFragmentInteractionListener) {
            mListener = (OnResourceListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getResourceList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnResourceListFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnResourceListFragmentInteractionListener(Resource item);

        Context getContext();
    }


    private void getResourceList() {
        /**
         GET List Resources
         **/
        showProgress(true);
        Call<ResourceList> loginResponseCall = apiInterface.doGetListResources();
        loginResponseCall.enqueue(new Callback<ResourceList>() {
            @Override
            public void onResponse(Call<ResourceList> call, Response<ResourceList> response) {
                Log.d("TAG", "onSuccess Code : " + response.code() + " Body : " + response.message());

                if (response.isSuccessful()) {
                    ResourceList resourceList = response.body();
                    if (resourceList != null) {
                        resourceRecyclerViewAdapter.setResourceList(resourceList.getData());
                        page = resourceList.getPage();
                        totalPage = resourceList.getTotalPages();
                        Log.d("TAG", "Resource List Size : " + resourceList.getData().size());
                    }

                } else {
                    Log.d("TAG", "Unsuccessful response : Error in receiving response");
                }
                showProgress(false);
            }

            @Override
            public void onFailure(Call<ResourceList> call, Throwable throwable) {
                Log.d("TAG", "onFailure");
                showProgress(false);
            }
        });
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
