package com.example.earningcoin.History;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.earningcoin.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {


    private List<HistoryModel> list;
    private Context context;

    public HistoryAdapter(List<HistoryModel> list){
        this.list = list;

    }


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from ( parent.getContext () )
                .inflate ( R.layout.history_item, parent,false );
        return new HistoryHolder ( view );



    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {

        holder.phoneTv.setText ( list.get ( position ).getPhone () );
        holder.statusTv.setText ( list.get ( position ).getStatus () );


        try {
            Glide.with ( holder.imageView.getContext ().getApplicationContext () )
                    .load ( list.get ( position ).getImage () )
                    .into ( holder.imageView );
        } catch (Exception e) {
            e.printStackTrace ();
        }


    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    class HistoryHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView phoneTv, statusTv;



        public HistoryHolder(@NonNull View itemView) {
            super ( itemView );


            imageView = (ImageView)itemView.findViewById ( R.id.image );
            phoneTv = (TextView) itemView.findViewById ( R.id.phone );
            statusTv = (TextView) itemView.findViewById ( R.id.status );


        }
    }
}
