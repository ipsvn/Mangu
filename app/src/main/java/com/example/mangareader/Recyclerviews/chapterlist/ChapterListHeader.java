package com.example.mangareader.Recyclerviews.chapterlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.mangareader.Favourites.FavouriteItem;
import com.example.mangareader.Favourites.Favourites;
import com.example.mangareader.R;
import com.example.mangareader.ValueHolders.SourceObjectHolder;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class ChapterListHeader extends RviewAdapterChapterlist.ViewHolder {

    public static final int TYPE = 0;
    private final ImageView favouriteStar;
    private final ImageView poster;
    private final TextView description;
    private final ImageView download;

    public ChapterListHeader(LayoutInflater inflater, @NonNull @NotNull ViewGroup parent, int layoutResource) {
        super(inflater, parent, layoutResource);
        this.favouriteStar = this.itemView.findViewById(R.id.favourite_star);
        this.poster = this.itemView.findViewById(R.id.poster);
        this.description = this.itemView.findViewById(R.id.description_text);
        this.download = this.itemView.findViewById(R.id.download_button);
    }


    public void bind(HeaderInfo data) {

        favouriteStar.setOnClickListener(v -> {

            String url = data.getMangaUrl();
            String img = data.getMangaImageUrl();


            // adds or removes from favourites
            Context context = favouriteStar.getContext();
            FavouriteItem favouriteItem = new FavouriteItem(
                    SourceObjectHolder.getSources(context).getClass().getName(), url, img, data.getMangaName(),
                    (int) Instant.now().getEpochSecond(), data.getReferer());
            Favourites.checkWhatNeedsToHappen(context, favouriteItem);

        });

        // I cannot be bothered making the favourite star work with the downloads.
        Activity activity = (Activity) favouriteStar.getContext();
        Intent intent = activity.getIntent();
        if (intent.getBooleanExtra("downloaded", false)) {
            favouriteStar.setVisibility(View.INVISIBLE);
        }

        // I disabled this as I have no use for it anymore.
        // See chapter_list_header.xml
        download.setOnClickListener(view -> {
            Log.d("lol", "YOU CLICKED ME");
        });

        // Currently uses internet and does not take into account the downloading
        if (data.getReferer() == null) {
            Glide.with(poster.getContext())
                    .load(data.getMangaImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(poster);
        } else {
            GlideUrl url = new GlideUrl(data.getMangaImageUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", data.getReferer())
                    .build());

            Glide.with(poster.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(poster);
        }

        description.setText(data.getDescription());

    }


}
