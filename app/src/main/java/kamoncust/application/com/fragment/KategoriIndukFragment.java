package kamoncust.application.com.fragment;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import kamoncust.application.com.kamoncust.R;

public class KategoriIndukFragment extends Fragment {

	static GridView gridViewKategori;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_kategori_induk, container, false);
        gridViewKategori = (GridView) rootView.findViewById(R.id.gridViewKategori);

		//ListKategoriAdapter kategorilistAdapter = new ListKategoriAdapter(getContext(), MainActivity.kategorilist, 0);
		//gridViewKategori.setAdapter(kategorilistAdapter);

        return rootView;
    }

}
