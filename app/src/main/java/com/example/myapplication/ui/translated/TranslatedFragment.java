package com.example.myapplication.ui.translated;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentTranslatedBinding;

public class TranslatedFragment extends Fragment {

    private Button btn_translated;
    private TextView translated_text;
    private FragmentTranslatedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TranslatedViewModel wordsViewModel =
                new ViewModelProvider(this).get(TranslatedViewModel.class);

        binding = FragmentTranslatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_translated = root.findViewById(R.id.btn_translated);
        translated_text = root.findViewById(R.id.translated_text);
        btn_translated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translated_text.setText("работает");
            }
        });

        return root;
    }
}