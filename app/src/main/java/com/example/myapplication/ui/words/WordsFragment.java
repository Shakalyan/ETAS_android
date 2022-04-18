package com.example.myapplication.ui.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWordsBinding;
import com.example.myapplication.ui.words.WordsViewModel;

public class WordsFragment extends Fragment {

    public WordsFragment(){
        super(R.layout.fragment_words);
    }

    private FragmentWordsBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WordsViewModel testsViewModel =
                new ViewModelProvider(this).get(WordsViewModel.class);

        binding = FragmentWordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}