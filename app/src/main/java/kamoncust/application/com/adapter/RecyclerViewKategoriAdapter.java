package kamoncust.application.com.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.widget.TextView;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.model.kategori;


public class RecyclerViewKategoriAdapter extends RecyclerView.Adapter<RecyclerViewKategoriAdapter.DataObjectHolder> {

    Context context;
    ArrayList<kategori> kategorilist;
    //ImageLoader imageLoader;
    //DisplayImageOptions options;

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //CircleImageView image;
        TextView title;

        public DataObjectHolder(View itemView) {
            super(itemView);

            //image = (CircleImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            //image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity) context).openProdukKategori(kategorilist.get(getAdapterPosition()));
        }

    }

    public RecyclerViewKategoriAdapter(Context context, ArrayList<kategori> kategorilist) {
        this.context = context;
        this.kategorilist = kategorilist;

        //imageLoader = ImageLoader.getInstance();
        //options = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listkategoriinduk, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.title.setText(kategorilist.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return kategorilist.size();
    }
}
