package cp3406.jcu.edu.au.edappt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>{
    private ArrayList<HighScoreItem> highscoreList;
    private HighScoreAdapter.OnHighScoreClickListener listener;

    public interface OnHighScoreClickListener {
        void onHighScoreClick(int position);
    }

    public void setOnHighScoreClickListener(OnHighScoreClickListener mListener) {
        listener = mListener;
    }

    public static class HighScoreViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView difficulty;
        public TextView time;
        public TextView score;

        public HighScoreViewHolder(View highscoreView, final OnHighScoreClickListener listener) {
            super(highscoreView);

            name = highscoreView.findViewById(R.id.name);
            difficulty = highscoreView.findViewById(R.id.difficulty);
            time = highscoreView.findViewById(R.id.time);
            score = highscoreView.findViewById(R.id.score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHighScoreClick(position);
                        }
                    }
                }
            });
        }
    }

    public HighScoreAdapter(ArrayList<HighScoreItem> exampleList) {
        highscoreList = exampleList;
    }


    @Override
    public HighScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_item, parent, false);
        HighScoreViewHolder evh = new HighScoreViewHolder(v, listener);
        return evh;
    }

    @Override
    public void onBindViewHolder(HighScoreViewHolder holder, int position) {
        HighScoreItem currentItem = highscoreList.get(position);
        holder.name.setText(currentItem.getName());
        holder.difficulty.setText(currentItem.getDifficulty());
        holder.time.setText(currentItem.getTime());
        holder.score.setText(currentItem.getScore());
    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }
}
