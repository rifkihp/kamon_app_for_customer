package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class KategoriFragment extends Fragment {

    public static GridView gridview;
    public static ProgressBar loading;
    public static LinearLayout retry;
    public static Button btnReload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kategori, container, false);
        gridview = rootView.findViewById(R.id.gridview);
        loading   = rootView.findViewById(R.id.pgbarLoading);
        retry     = rootView.findViewById(R.id.loadMask);
        btnReload = rootView.findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity()).loadDataKategori();
            }
        });

        ((MainActivity) getActivity()).loadDataKategori();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
