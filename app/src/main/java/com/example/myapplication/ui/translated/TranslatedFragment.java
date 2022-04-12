package com.example.myapplication.ui.translated;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class TranslatedFragment extends Fragment {

    private Button btn_translated;
    private TextView translated_text;

    public TranslatedFragment(){
        super(R.layout.fragment_translated);
        btn_translated = btn_translated.findViewById(R.id.btn_translated);
        translated_text = translated_text.findViewById(R.id.translated_text);
        btn_translated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translated_text.setText("работает");
            }
        });
    }

    /*private FragmentTranslatedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TranslatedViewModel translatedViewModel =
                new ViewModelProvider(this).get(TranslatedViewModel.class);

        binding = FragmentTranslatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        translatedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}