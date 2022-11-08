package com.example.softwarelll.Productos;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.softwarelll.R;

import java.util.ArrayList;
import java.util.Locale;

public class ProductosFragmento extends ListFragment {

    public AdaptadorProductos adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.update();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new AdaptadorProductos (getActivity());
        setListAdapter(adapter);

        this.update();
        this.reset();
    }

    private void reset() {
        for (int i = 0; i < this.adapter.getCount() ; i++) {
            this.adapter.getItem(i).setQuantity(0);
        }
    }

    public void update() {
        // load initial product settings

        ArrayList<Productos> dbProducts = Productos.fetchProducts();
        this.adapter.clear();
        this.adapter.addAll(dbProducts);
        this.adapter.notifyDataSetChanged();
        TextView total = getActivity().findViewById(R.id.totalAccumulator);
        total.setText(String.format(Locale.CANADA, "$%.2f", adapter.total));
    }
}