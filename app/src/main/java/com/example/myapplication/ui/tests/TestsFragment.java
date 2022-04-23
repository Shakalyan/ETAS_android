package com.example.myapplication.ui.tests;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.data.CurrentTestData;
import com.example.myapplication.data.CurrentUserData;
import com.example.myapplication.data.TestWindow;
import com.example.myapplication.databinding.FragmentTestsBinding;
import com.example.myapplication.model.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TestsFragment extends Fragment {

    public TestsFragment() {
        super(R.layout.fragment_tests);
    }

    private FragmentTestsBinding binding;

    //Options menu
    private ViewFlipper testsFlipper;
    private Spinner testsSpinner;
    private Spinner languagesSpinner;
    private Button startButton;
    private EditText wordsCountEt;

    //First test
    private TextView ftWordTw;
    private TextView ftWordsRest;
    private ArrayList<Button> ftVariants = new ArrayList<>();
    private Button ftFinishTestButton;
    private int initialVariantsColor;

    //Second test
    private TextView stWordTw;
    private EditText stInputEt;
    private Button stSubmitButton;
    private Button stEndTestButton;
    private TextView stWordsRestTw;
    private int initialInputColor;

    //test finish
    private TextView correctAnswersTw;
    private Button exitTestButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TestsViewModel testsViewModel =
                new ViewModelProvider(this).get(TestsViewModel.class);

        binding = FragmentTestsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        testsFlipper = root.findViewById(R.id.testsFlipper);
        testsSpinner = root.findViewById(R.id.tests_spinner);
        startButton = root.findViewById(R.id.startButton);
        wordsCountEt = root.findViewById(R.id.wordsCount);
        languagesSpinner = root.findViewById(R.id.languages_spinner);

        ftWordTw = root.findViewById(R.id.ft_word_field);
        ftVariants.add(root.findViewById(R.id.variant1));
        ftVariants.add(root.findViewById(R.id.variant2));
        ftVariants.add(root.findViewById(R.id.variant3));
        ftVariants.add(root.findViewById(R.id.variant4));
        ftFinishTestButton = root.findViewById(R.id.test_finish_button);
        ftWordsRest = root.findViewById(R.id.ft_words_rest);
        initialVariantsColor = R.color.btn;

        stWordTw = root.findViewById(R.id.st_word_field);
        stInputEt = root.findViewById(R.id.st_input_field);
        stSubmitButton = root.findViewById(R.id.st_submit_button);
        stEndTestButton = root.findViewById(R.id.st_finish_button);
        stWordsRestTw = root.findViewById(R.id.st_words_rest);
        initialInputColor = stInputEt.getHighlightColor();
        ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(initialInputColor));

        correctAnswersTw = root.findViewById(R.id.test_correct_answers_tw);
        exitTestButton = root.findViewById(R.id.test_exit_button);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.tests_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testsSpinner.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUserData.currentDictionaryIsNull(true, getContext()))
                    return;
                if(wordsCountEt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Введите количество слов", Toast.LENGTH_LONG).show();
                    return;
                }
                int stepsCount = Integer.parseInt(wordsCountEt.getText().toString());
                if(stepsCount <= 0 || stepsCount > CurrentUserData.getCurrentDictionary().getTranslations().size()) {
                    Toast.makeText(getContext(), "Неверное количество слов", Toast.LENGTH_LONG).show();
                    return;
                }

                CurrentTestData.setTestStarted(true);
                CurrentTestData.setCurrentTestWindow((testsSpinner.getSelectedItemId() == 0)? TestWindow.FIRST_TEST : TestWindow.SECOND_TEST);
                CurrentTestData.setTestReversed(languagesSpinner.getSelectedItemId() == 1);

                CurrentTestData.setStepsCount(stepsCount);
                CurrentTestData.setRestStepsCount(stepsCount);
                ArrayList<Translation> translations = getTranslationsList();
                CurrentTestData.setWordsCount(translations.size());
                CurrentTestData.setRestWordsCount(translations.size());
                CurrentTestData.setCurrentTranslations(translations);

                generateTestStep();
                loadTestWindow();
            }
        });

        View.OnClickListener variantsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentTestData.isAnswerSubmitted()) {
                    for(Button variant : ftVariants)
                        variant.setBackgroundColor(initialVariantsColor);
                    if(CurrentTestData.getRestStepsCount() > 0) {
                        CurrentTestData.setAnswerSubmitted(false);
                        generateTestStep();
                        loadTestWindow();
                    }
                    else {
                        CurrentTestData.setCurrentTestWindow(TestWindow.FINISH);
                        loadTestWindow();
                    }
                }
                else {
                    if(((Button)view).getText().toString().equals(CurrentTestData.getCurrentAnswer()))
                        CurrentTestData.increaseCorrectAnswers();
                    for(Button variant : ftVariants)
                        if(variant.getText().toString().equals(CurrentTestData.getCurrentAnswer()))
                            variant.setBackgroundColor(Color.GREEN);
                        else
                            variant.setBackgroundColor(Color.RED);
                    CurrentTestData.setAnswerSubmitted(true);
                 }
            }
        };

        for(Button variant : ftVariants)
            variant.setOnClickListener(variantsListener);

        ftFinishTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentTestData.setCurrentTestWindow(TestWindow.FINISH);
                loadTestWindow();
            }
        });

        exitTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentTestData.refresh();
                loadTestWindow();
            }
        });

        stEndTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentTestData.setCurrentTestWindow(TestWindow.FINISH);
                loadTestWindow();
            }
        });

        stSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentTestData.isAnswerSubmitted()) {
                    ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(initialInputColor));
                    stInputEt.setText("");
                    if(CurrentTestData.getRestStepsCount() > 0) {
                        CurrentTestData.setAnswerSubmitted(false);
                        generateTestStep();
                        loadTestWindow();
                    }
                    else {
                        CurrentTestData.setCurrentTestWindow(TestWindow.FINISH);
                        loadTestWindow();
                    }
                }
                else {
                    String answer = CurrentTestData.getCurrentAnswer();
                    if(stInputEt.getText().toString().equals(answer)) {
                        CurrentTestData.increaseCorrectAnswers();

                        ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(Color.GREEN));
                    }
                    else {
                        stInputEt.setText(answer);
                        ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(Color.RED));
                    }
                    CurrentTestData.setAnswerSubmitted(true);
                }
            }
        });

        return root;
    }

    private ArrayList<Translation> getTranslationsList() {
        return new ArrayList<>(CurrentUserData.getCurrentDictionary().getTranslations());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTestWindow();
    }

    private void loadTestWindow() {
        testsFlipper.setDisplayedChild(CurrentTestData.getCurrentTestWindow().getValue());

        TestWindow currentTestWindow = CurrentTestData.getCurrentTestWindow();
        if(currentTestWindow.equals(TestWindow.START)) {
            String hint = "max: " + CurrentUserData.getCurrentDictionary().getTranslations().size();
            wordsCountEt.setHint(hint);
        }
        else if(currentTestWindow.equals(TestWindow.FIRST_TEST)) {
            Random random = new Random();
            ftWordTw.setText(CurrentTestData.getCurrentWord());
            String restWordsText = "Осталось слов: " + CurrentTestData.getRestStepsCount();
            ftWordsRest.setText(restWordsText);
            int vBound = 4;
            for(int i = 0; i < 4; ++i) {
                int vIndex = random.nextInt(vBound);
                ftVariants.get(i).setText(CurrentTestData.getCurrentVariants().get(vIndex));
                Collections.swap(CurrentTestData.getCurrentVariants(), vIndex, vBound-- - 1);
            }
            if(CurrentTestData.isAnswerSubmitted())
                for(Button variant : ftVariants)
                    if(variant.getText().toString().equals(CurrentTestData.getCurrentAnswer()))
                        variant.setBackgroundColor(Color.GREEN);
                    else
                        variant.setBackgroundColor(Color.RED);
        }
        else if(currentTestWindow.equals(TestWindow.SECOND_TEST)) {
            stWordTw.setText(CurrentTestData.getCurrentWord());
            String restWordsText = "Осталось слов: " + CurrentTestData.getRestStepsCount();
            stWordsRestTw.setText(restWordsText);
            if(CurrentTestData.isAnswerSubmitted()) {
                String answer = CurrentTestData.getCurrentAnswer();
                if(stInputEt.getText().toString().equals(answer)) {
                    CurrentTestData.increaseCorrectAnswers();
                    ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(Color.GREEN));
                }
                else {
                    stInputEt.setText(answer);
                    ViewCompat.setBackgroundTintList(stInputEt, ColorStateList.valueOf(Color.RED));
                }
            }
        }
        else if(currentTestWindow.equals(TestWindow.FINISH)) {
            String text = String.format("Правильных ответов: %d/%d", CurrentTestData.getCorrectAnswers(), CurrentTestData.getStepsCount());
            correctAnswersTw.setText(text);
        }
    }

    private void generateTestStep() {
        Random random = new Random();
        int translationIndex = random.nextInt(CurrentTestData.getRestWordsCount());
        Translation translation = CurrentTestData.getCurrentTranslations().get(translationIndex);
        CurrentTestData.setCurrentWord((CurrentTestData.isTestReversed())? translation.getSlValue() : translation.getFlValue());
        CurrentTestData.setCurrentAnswer((!CurrentTestData.isTestReversed())? translation.getSlValue() : translation.getFlValue());
        Collections.swap(CurrentTestData.getCurrentTranslations(), translationIndex, CurrentTestData.getRestWordsCount() - 1);
        Collections.swap(CurrentTestData.getCurrentTranslations(), CurrentTestData.getRestWordsCount() - 1, CurrentTestData.getWordsCount() - 1);
        CurrentTestData.decreaseRestWordsCount();
        CurrentTestData.decreaseStepsCount();

        if(CurrentTestData.getCurrentTestWindow().equals(TestWindow.FIRST_TEST)) {
            ArrayList<String> variants = new ArrayList<>();
            ArrayList<Translation> translationsCopy = (ArrayList<Translation>)CurrentTestData.getCurrentTranslations().clone();

            int wordsCount = CurrentTestData.getWordsCount() - 1;
            Collections.swap(translationsCopy, translationIndex, wordsCount-- - 1);
            variants.add(CurrentTestData.getCurrentAnswer());

            for(int i = 0; i < 3; ++i) {
                int variantIndex = random.nextInt(wordsCount);
                Translation variant = translationsCopy.get(variantIndex);
                variants.add((!CurrentTestData.isTestReversed())? variant.getSlValue() : variant.getFlValue());
                Collections.swap(translationsCopy, variantIndex, wordsCount-- - 1);
            }
            Log.i("VARIANTS",  variants.toString());
            CurrentTestData.setCurrentVariants(variants);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}