package com.gsmasdk.gsmatest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class CustomUseCaseRecyclerAdapter extends RecyclerView.Adapter<CustomUseCaseRecyclerAdapter.ViewHolder> {
    private Context context;
    private int status;
    private ArrayList<String> mSelectedPosition = new ArrayList<>();
    private String[] useCaseArray;
    private ItemClickListener mClickListener;

    CustomUseCaseRecyclerAdapter(Context context, String[] useCaseArray) {
        this.context = context;
        this.useCaseArray = useCaseArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usecase_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.useCase.setText(useCaseArray[position]);
        if (mSelectedPosition.contains(String.valueOf(position))) {
            if (status == 0) {
                holder.ivStatus.setImageResource(R.drawable.ic_pending);
            } else if (status == 1) {
                holder.ivStatus.setImageResource(R.drawable.ic_check);
            } else {
                holder.ivStatus.setImageResource(R.drawable.ic_fail);
            }
        }else{
            holder.ivStatus.setImageResource(R.drawable.ic_pending);
        }
    }


    @Override
    public int getItemCount() {
        return useCaseArray.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView useCase;
        private ImageView ivStatus;

        public ViewHolder(final View itemView) {
            super(itemView);
            useCase = (TextView) itemView.findViewById(R.id.useCaseItem);
            ivStatus = (ImageView) itemView.findViewById(R.id.ivTestStatus);
            ivStatus.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            mSelectedPosition.add(String.valueOf(getAdapterPosition()));
        }
    }

    public void setStatus(int status,int position) {
        this.status = status;
        notifyItemChanged(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
