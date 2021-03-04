package kamoncust.application.com.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import kamoncust.application.com.adapter.ListKategoriAdapter;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.model.kategori;

public class KategoriLevel2Fragment extends Fragment {

    static GridView gridViewKategori;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kategori_induk, container, false);
        gridViewKategori = (GridView) rootView.findViewById(R.id.gridViewKategori);

		return rootView;
    }

    public static void setAdapter(Context context, ArrayList<kategori> result) {
        ListKategoriAdapter kategorilistAdapter = new ListKategoriAdapter(context, result, 1);
        gridViewKategori.setAdapter(kategorilistAdapter);

    }
}
