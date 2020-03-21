package edu.temple.pickacolor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class PaletteFragment extends Fragment {
    String[] colorNames;
    String[] colors;
    int selected = 0;

    private static final String COLOR_NAMES = "colorNames";
    private static final String COLORS = "colors";
    OnColorSelectedListener colorSelectedListener;

    public interface OnColorSelectedListener {
        void onColorSelected(int position);
    }
    public PaletteFragment() {
    }

    public static PaletteFragment newInstance(String[] colorNames, String[] colors) {
        PaletteFragment paletteFragment = new PaletteFragment();
        Bundle args = new Bundle();
        args.putStringArray(COLOR_NAMES, colorNames);
        args.putStringArray(COLORS, colors);
        paletteFragment.setArguments(args);
        return paletteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            colors = bundle.getStringArray(COLORS);
            colorNames = bundle.getStringArray(COLOR_NAMES);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.palette_fragment_layout, container, false);
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(new ColorAdapter(getActivity()));
        spinner.setSelection(selected, false);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selected = position;
                colorSelectedListener.onColorSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof OnColorSelectedListener) {
                colorSelectedListener = (OnColorSelectedListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnColorSelectedListener");
        }
    }
}
