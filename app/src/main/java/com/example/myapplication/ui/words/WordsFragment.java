package com.example.myapplication.ui.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.data.CurrentUserData;
import com.example.myapplication.databinding.FragmentWordsBinding;
import com.example.myapplication.model.Dictionary;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.Translation;
import com.example.myapplication.service.DictionaryService;
import com.example.myapplication.service.TranslationService;

import java.util.ArrayList;

public class WordsFragment extends Fragment {

    public WordsFragment(){
        super(R.layout.fragment_words);
    }

    private FragmentWordsBinding binding;

    private Button createButton;
    private Button deleteButton;
    private Button chooseButton;
    private Button deleteWordsButton;
    private TextView currentDictionaryTW;
    private LinearLayout wordsList;
    private EditText etDictionaryName;
    private Spinner dictionariesSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WordsViewModel testsViewModel =
                new ViewModelProvider(this).get(WordsViewModel.class);

        binding = FragmentWordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createButton = root.findViewById(R.id.btn_create);
        deleteButton = root.findViewById(R.id.btn_delete);
        chooseButton = root.findViewById(R.id.btn_choice);
        deleteWordsButton = root.findViewById(R.id.btn_delete_words);
        currentDictionaryTW = root.findViewById(R.id.current_dict_tw);
        wordsList = root.findViewById(R.id.words_list);
        etDictionaryName = root.findViewById(R.id.et_dictionaryName);
        dictionariesSpinner = root.findViewById(R.id.dictioinaries_spinner);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etDictionaryName.getText().toString();
                Dictionary dictionary = new Dictionary(name);
                if(!DictionaryService.createDictionary(CurrentUserData.getUser(), dictionary)) {
                    Toast.makeText(getContext(), "Incorrect dictionary name",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                while(!DictionaryService.responseIsPresent()) {

                }

                Response response = DictionaryService.retrieveResponse();

                if(response.getStatusCode() != 200) {
                    Toast.makeText(getContext(), response.getData(), Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    dictionary.setId(Long.parseLong(response.getData()));
                    Toast.makeText(getContext(), "Successfully created", Toast.LENGTH_LONG).show();
                }

                CurrentUserData.getUser().getDictionaries().add(dictionary);
                updateSpinner();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUserData.currentDictionaryIsNull(true, getContext()))
                    return;

                Dictionary dictionary = CurrentUserData.getCurrentDictionary();

                DictionaryService.deleteDictionary(CurrentUserData.getUser(), dictionary);
                while(!DictionaryService.responseIsPresent()) {

                }
                Response response = DictionaryService.retrieveResponse();
                Toast.makeText(getContext(), response.getData(), Toast.LENGTH_LONG).show();
                if(response.getStatusCode() != 200) {
                    return;
                }

                CurrentUserData.getUser().getDictionaries().remove(dictionary);
                updateSpinner();
                updateShowWordsButtonText();
            }
        });

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedDictionaryIsNull())
                    return;
                Dictionary dictionary = getSelectedDictionary();
                CurrentUserData.setCurrentDictionary(dictionary);
                Toast.makeText(getContext(), String.format("Current dictionary is %s", dictionary.getName()), Toast.LENGTH_LONG).show();
                updateShowWordsButtonText();
            }
        });

        dictionariesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateWordsList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        deleteWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUserData.currentDictionaryIsNull(true, getContext()))
                    return;

                ArrayList<Translation> translationsToDelete = new ArrayList<>();
                ArrayList<Translation> allTranslations = new ArrayList<>(CurrentUserData.getCurrentDictionary().getTranslations());
                for(int i = 0; i < wordsList.getChildCount(); ++i) {
                    if(((CheckBox)wordsList.getChildAt(i)).isChecked())
                        translationsToDelete.add(allTranslations.get(i));
                }

                if(!TranslationService.deleteTranslations(   CurrentUserData.getUser(),
                                                            translationsToDelete.toArray(new Translation[0]),
                                                            CurrentUserData.getCurrentDictionary().getId())) {
                    Toast.makeText(getContext(), "Nothing chosen", Toast.LENGTH_LONG).show();
                    return;
                }

                while(!TranslationService.responseIsPresent()) {

                }

                Response response = TranslationService.retrieveResponse();
                Toast.makeText(getContext(), response.getData(), Toast.LENGTH_LONG).show();
                if(response.getStatusCode() != 200)
                    return;

                CurrentUserData.getCurrentDictionary().getTranslations().removeAll(translationsToDelete);
                CurrentUserData.setCurrentDictionary(null);
                updateShowWordsButtonText();
                updateWordsList();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSpinner();
        updateWordsList();
        updateShowWordsButtonText();
    }

    private Dictionary getSelectedDictionary() {
        int index = (int)dictionariesSpinner.getSelectedItemId();
        ArrayList<Dictionary> dl = new ArrayList<Dictionary>(CurrentUserData.getUser().getDictionaries());
        if(dl.size() <= index)
            return null;
        return dl.get(index);
    }

    private void updateShowWordsButtonText() {
        if(CurrentUserData.currentDictionaryIsNull(false, getContext()))
            return;
        String text = "Текущий словарь: " + CurrentUserData.getCurrentDictionary().getName();
        currentDictionaryTW.setText(text);
    }

    private boolean selectedDictionaryIsNull() {
        if(getSelectedDictionary() == null) {
            Toast.makeText(getContext(), String.format("Selected dictionary is null"), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void updateWordsList() {
        Dictionary selectedDictionary = getSelectedDictionary();
        if(selectedDictionary == null)
            return;

        wordsList.removeAllViews();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int textSize = 20;
        for(Translation t : selectedDictionary.getTranslations()) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(String.format("%s - %s", t.getFlValue(), t.getSlValue()));
            cb.setLayoutParams(lp);
            cb.setTextSize(textSize);
            wordsList.addView(cb);
        }
    }

    private void updateSpinner() {
        ArrayList<String> dictsNames = new ArrayList<>();
        for(Dictionary d : CurrentUserData.getUser().getDictionaries())
            dictsNames.add(d.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dictsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dictionariesSpinner.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}