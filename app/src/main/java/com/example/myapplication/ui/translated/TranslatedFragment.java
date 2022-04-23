package com.example.myapplication.ui.translated;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.data.CurrentUserData;
import com.example.myapplication.databinding.FragmentTranslatedBinding;
import com.example.myapplication.model.Response;
import com.example.myapplication.model.Translation;
import com.example.myapplication.model.User;
import com.example.myapplication.service.TranslationService;

public class TranslatedFragment extends Fragment {

    private Button btn_translated;
    private Button saveButton;
    private Button reverseButton;
    private TextView translated_text;
    private TextView targetLanTw;
    private TextView sourceLanTw;
    private FragmentTranslatedBinding binding;
    private EditText input_text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TranslatedViewModel translatedViewModel =
                new ViewModelProvider(this).get(TranslatedViewModel.class);

        binding = FragmentTranslatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        input_text = root.findViewById(R.id.input_translated);
        btn_translated = root.findViewById(R.id.btn_translated);
        translated_text = root.findViewById(R.id.translated_text);
        saveButton = root.findViewById(R.id.btn_save);
        reverseButton = root.findViewById(R.id.btn_reverse);
        sourceLanTw = root.findViewById(R.id.tw_source_lan);
        targetLanTw = root.findViewById(R.id.tw_target_lan);

        btn_translated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sentence = input_text.getText().toString();
                String sourceLan = (CurrentUserData.isTranslationReversed())? "en" : "ru";
                String targetLan = (!CurrentUserData.isTranslationReversed())? "en" : "ru";
                TranslationService.translate(CurrentUserData.getUser(), sentence, sourceLan, targetLan);
                while(!TranslationService.responseIsPresent()) {
                }

                Response response = TranslationService.retrieveResponse();
                translated_text.setText(response.getData());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentDictionaryIsNull())
                    return;

                String flValue = input_text.getText().toString();
                String slValue = translated_text.getText().toString();
                Translation translation;
                if(CurrentUserData.isTranslationReversed())
                    translation = new Translation(slValue, flValue);
                else
                    translation = new Translation(flValue, slValue);

                if(!TranslationService.addTranslation(CurrentUserData.getUser(),
                                                translation,
                                                CurrentUserData.getCurrentDictionary().getId())) {
                    Toast.makeText(getContext(), "Empty input or output", Toast.LENGTH_LONG).show();
                }

                while(!TranslationService.responseIsPresent()) {

                }
                Response response = TranslationService.retrieveResponse();

                if(response.getStatusCode() != 200) {
                    Toast.makeText(getContext(), response.getData(), Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    translation.setId(Long.parseLong(response.getData()));
                    Toast.makeText(getContext(), "Successfully saved", Toast.LENGTH_LONG).show();
                }

                CurrentUserData.getCurrentDictionary().getTranslations().add(translation);
            }
        });

        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUserData.setTranslationReversed(!CurrentUserData.isTranslationReversed());
                String temp = input_text.getText().toString();
                input_text.setText(translated_text.getText());
                translated_text.setText(temp);
                updateLanguages();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        updateLanguages();
        super.onResume();
    }

    private void updateLanguages() {
        sourceLanTw.setText((CurrentUserData.isTranslationReversed())? "EN" : "RU");
        targetLanTw.setText((!CurrentUserData.isTranslationReversed())? "EN" : "RU");
    }

    private boolean currentDictionaryIsNull() {
        if(CurrentUserData.getCurrentDictionary() == null) {
            Toast.makeText(getContext(), String.format("Chosen dictionary is null"), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}