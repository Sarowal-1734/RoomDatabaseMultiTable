package com.sarowal.roomdatabasemultitable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarowal.roomdatabasemultitable.Database.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    //for use onItemClickListener from MainActivity
    private OnItemClickListener listener;

    private List<Expense> expenses = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        holder.tvIncome.setText(String.valueOf(expenses.get(position).getExpenseAmount()));
        holder.tvTitle.setText(expenses.get(position).getExpenseItem());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(expenses.get(position).getExpenseDate());
        holder.tvDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    //For LiveData
    public void setExpenses(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIncome, tvTitle, tvDate;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIncome = itemView.findViewById(R.id.tvPriority);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(expenses.get(getAdapterPosition()));
                }
            });
        }
    }

    //for use onItemClickListener from MainActivity
    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
