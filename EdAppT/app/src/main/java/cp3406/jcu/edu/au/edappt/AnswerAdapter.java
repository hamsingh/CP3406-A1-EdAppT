package cp3406.jcu.edu.au.edappt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

// This class helps to run the recycler view
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private ArrayList<AnswerItem> answerList;
    private OnAnswerClickListener listener;

    public interface OnAnswerClickListener {
        void onAnswerClick(int position);
    }

    public void setOnItemClickListener(OnAnswerClickListener mListener) {
        listener = mListener;
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;

        public AnswerViewHolder(View answerView, final OnAnswerClickListener listener) {
            super(answerView);
            answer = answerView.findViewById(R.id.answerView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAnswerClick(position);
                        }
                    }
                }
            });
        }
    }

    public AnswerAdapter(ArrayList<AnswerItem> exampleList) {
        answerList = exampleList;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        AnswerViewHolder evh = new AnswerViewHolder(v, listener);
        return evh;
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        AnswerItem currentItem = answerList.get(position);
        holder.answer.setText(currentItem.getAnswer());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }
}
