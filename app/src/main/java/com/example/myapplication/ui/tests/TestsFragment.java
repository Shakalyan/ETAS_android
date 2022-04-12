package com.example.myapplication.ui.tests;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class TestsFragment extends Fragment {

    public TestsFragment(){
        super(R.layout.fragment_tests);
    }

    /*private FragmentTestsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TestsViewModel testsViewModel =
                new ViewModelProvider(this).get(TestsViewModel.class);

        binding = FragmentTestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.helloTests;
        testsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}