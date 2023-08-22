package nz.ac.aucklanduni.se306project1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nz.ac.aucklanduni.se306project1.R;
import nz.ac.aucklanduni.se306project1.activities.HomeActivity;
import nz.ac.aucklanduni.se306project1.activities.ShoppingCartActivity;
import nz.ac.aucklanduni.se306project1.activities.WatchlistActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomNavigationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomNavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomNavigationFragment newInstance(final String param1, final String param2) {
        final BottomNavigationFragment fragment = new BottomNavigationFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            this.mParam1 = this.getArguments().getString(ARG_PARAM1);
            this.mParam2 = this.getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        final BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.navigation_home) {
                final Intent homeIntent = new Intent(BottomNavigationFragment.this.getActivity(), HomeActivity.class);
                BottomNavigationFragment.this.startActivity(homeIntent);
                return true;
            } else if (id == R.id.navigation_watchlist) {
                final Intent watchlistIntent = new Intent(BottomNavigationFragment.this.getActivity(), WatchlistActivity.class);
                BottomNavigationFragment.this.startActivity(watchlistIntent);
                return true;
            } else if (id == R.id.navigation_cart) {
                final Intent cartIntent = new Intent(BottomNavigationFragment.this.getActivity(), ShoppingCartActivity.class);
                BottomNavigationFragment.this.startActivity(cartIntent);
                return true;
            }
            return false;
        });

        return rootView;
    }
}