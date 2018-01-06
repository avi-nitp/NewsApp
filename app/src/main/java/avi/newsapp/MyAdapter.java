package avi.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by AVI on 04-Jan-18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



    private ArrayList<ArticleModel> articles;
    private Context mContext;
    private int lastPosition = -1;

    public MyAdapter(Context mContext, ArrayList<ArticleModel> articles) {
        this.mContext = mContext;
        this.articles = articles;
    }

    /*
    ** inflating the cardView.
    **/
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        String title = articles.get(position).getTitle();
        if (title.endsWith("- Times of India")) {
            title = title.replace("- Times of India", "");
        } else if(title.endsWith(" - Firstpost")) {
            title = title.replace(" - Firstpost", "");
        }

        holder.tv_card_main_title.setText(title);

        Glide.with(mContext)
                .load(articles.get(position).getUrlToImage())
                .thumbnail(0.1f)
                .centerCrop()
                .error(R.drawable.ic_placeholder)
                .into(holder.img_card_main);
    }

    /*
    ** Last parameter for binding the articles in OnBindViewHolder.
    **/
    @Override
    public int getItemCount() {
        return articles.size();
    }

    /*
    ** ViewHolder class which holds the different views in the recyclerView .
    **/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_card_main_title;
        private ImageView img_card_main;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            tv_card_main_title = view.findViewById(R.id.tv_card_main_title);
            img_card_main = view.findViewById(R.id.img_card_main);
            cardView = view.findViewById(R.id.card_row);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String headLine = articles.get(getAdapterPosition()).getTitle();
            if (headLine.endsWith(" - Times of India")) {
                headLine = headLine.replace(" - Times of India", "");
            } else if(headLine.endsWith(" - Firstpost")) {
                headLine = headLine.replace(" - Firstpost", "");
            }
            String description = articles.get(getAdapterPosition()).getDescription();
            String imgURL = articles.get(getAdapterPosition()).getUrlToImage();
            String URL = articles.get(getAdapterPosition()).getUrl();

            Intent intent = new Intent(mContext, MyActivity.class);

            intent.putExtra("i_headline", headLine);
            intent.putExtra("i_description", description);
            intent.putExtra("i_imgurl", imgURL);
            intent.putExtra("i_url", URL);

            mContext.startActivity(intent);

        }
    }
}
