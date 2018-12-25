package com.androidarchcomp.mvpexample.adapters;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidarchcomp.mvpexample.R;
import com.androidarchcomp.mvpexample.fragments.ResourceListFragment;
import com.androidarchcomp.mvpexample.model.Resource;
import com.androidarchcomp.mvpexample.model.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link User} and makes a call to the
 * specified {@link ResourceListFragment.OnResourceListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ResourceRecyclerViewAdapter extends RecyclerView.Adapter<ResourceRecyclerViewAdapter.ViewHolder> {

    private List<Resource> mValues;
    private final ResourceListFragment.OnResourceListFragmentInteractionListener mListener;

    public ResourceRecyclerViewAdapter(ResourceListFragment.OnResourceListFragmentInteractionListener listener) {
        mListener = listener;
    }

    public void setResourceList(List<Resource> items){
        if(mValues != null){
            mValues.clear();
        }
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(holder.mItem.getId()));
        holder.mContentView.setText(holder.mItem.getName());
        holder.mYearView.setText(String.valueOf(holder.mItem.getYear()));
        holder.mPhotonValueView.setText(holder.mItem.getPantoneValue());
        String colorCode = mValues.get(position).getColor();
        holder.mLinearLayoutCompat.setBackgroundColor(Color.parseColor(colorCode));

//        Picasso.get()
//                .load(url)
////                .resize(50, 50)
////                .centerCrop()
//                .placeholder(android.R.drawable.sym_def_app_icon)
//                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnResourceListFragmentInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final AppCompatTextView mIdView;
        public final AppCompatTextView mContentView;
        public final LinearLayoutCompat mLinearLayoutCompat;
        public final AppCompatTextView mYearView;
        public final AppCompatTextView mPhotonValueView;
        public Resource mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (AppCompatTextView) view.findViewById(R.id.item_id);
            mContentView = (AppCompatTextView) view.findViewById(R.id.item_name);
            mYearView = (AppCompatTextView) view.findViewById(R.id.item_year);
            mPhotonValueView = (AppCompatTextView) view.findViewById(R.id.item_pantone_value);
            mLinearLayoutCompat = (LinearLayoutCompat) view.findViewById(R.id.linearLayout);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
