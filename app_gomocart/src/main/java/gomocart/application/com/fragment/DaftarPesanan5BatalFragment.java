package gomocart.application.com.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class DaftarPesanan5BatalFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    public static ListView listViewOrder;
    public static ProgressBar loading;
    public static LinearLayout retry;
    static TextView btnReload;
    int index = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_taborderlist, container, false);

        listViewOrder      = rootView.findViewById(R.id.listview);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
        loading            = rootView.findViewById(R.id.pgbarLoading);
        retry              = rootView.findViewById(R.id.loadMask);
        btnReload          = rootView.findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity()).loadOrderlist(index, false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity) getActivity()).loadOrderlist(index, true);
            }
        });

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).loadOrderlist(index, true);
    }
}
