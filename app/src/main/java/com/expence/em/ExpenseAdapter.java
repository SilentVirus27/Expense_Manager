package com.expence.em;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private OnItemsCLick onItemsCLick;
    private List<ExpenseModel> expenseModelList;

    public ExpenseAdapter(Context context,OnItemsCLick onItemsCLick) {
        this.context = context;
        expenseModelList=new ArrayList<>();
        this.onItemsCLick=onItemsCLick;
    }
    public void add(ExpenseModel expenseModel){
        expenseModelList.add(expenseModel);
        notifyDataSetChanged();
    }
    public void clear(){
        expenseModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseModel expenseModel=expenseModelList.get(position);
        holder.note.setText(expenseModel.getNote());
        holder.category.setText(expenseModel.getCategory());
        holder.amount.setText(String.valueOf(expenseModel.getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemsCLick.onClick(expenseModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView note,category,amount,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note);
            category=itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.date);
        }
    }
}
