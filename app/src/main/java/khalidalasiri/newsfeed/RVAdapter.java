package khalidalasiri.newsfeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kasir on 1/25/2018.
 */

class RVAdapter extends RecyclerView.Adapter<RVAdapter.VH> {

    Context context;
    List<News> newsList;

    public RVAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.date.setText(newsList.get(position).getDate());
        holder.title.setText(newsList.get(position).getTitle());
        holder.section.setText(newsList.get(position).getSection());
        holder.author.setText(newsList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView date;
        TextView title;
        TextView section;
        TextView author;

        public VH(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            title = itemView.findViewById(R.id.title);
            section = itemView.findViewById(R.id.section);
            author = itemView.findViewById(R.id.author);
        }
    }
}
