package com.sarowal.roomdatabasemultitable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarowal.roomdatabasemultitable.Database.Expense;
import com.sarowal.roomdatabasemultitable.Database.Income;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.sarowal.roomdatabasemultitable.MainActivity.n;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeExpenseViewHolder> {
    private OnItemClickListener listener;
    private List<Income> incomes = new ArrayList<>();

    @NonNull
    @Override
    public IncomeExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new IncomeExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeExpenseViewHolder holder, int position) {
            holder.tvIncome.setText(String.valueOf(incomes.get(position).getIncomeAmount()));
            holder.tvTitle.setText(incomes.get(position).getIncomeSource());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = simpleDateFormat.format(incomes.get(position).getIncomeDate());
            holder.tvDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    //For LiveData
    public void setIncomes(List<Income> incomes){
        this.incomes = incomes;
        notifyDataSetChanged();
    }

    public class IncomeExpenseViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIncome, tvTitle, tvDate;

        public IncomeExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIncome = itemView.findViewById(R.id.tvPriority);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(incomes.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Income income);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
