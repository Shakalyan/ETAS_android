package com.example.myapplication.data;

import com.example.myapplication.model.Translation;

import java.util.ArrayList;

public class CurrentTestData {

    private static boolean testStarted = false;
    private static TestWindow currentTestWindow = TestWindow.START;
    private static boolean testReversed = false;
    private static int wordsCount = 0;
    private static int stepsCount = 0;
    private static int restStepsCount = 0;
    private static int restWordsCount = 0;
    private static String currentWord = "";
    private static String currentAnswer = "";
    private static int correctAnswers = 0;
    private static ArrayList<String> currentVariants = new ArrayList<>();
    private static ArrayList<Translation> currentTranslations = new ArrayList<>();
    private static boolean answerSubmitted = false;

    public static void refresh() {
        testStarted = false;
        currentTestWindow = TestWindow.START;
        testReversed = false;
        wordsCount = 0;
        stepsCount = 0;
        restStepsCount = 0;
        restWordsCount = 0;
        currentWord = "";
        currentAnswer = "";
        correctAnswers = 0;
        currentVariants = new ArrayList<>();
        currentTranslations = new ArrayList<>();
        answerSubmitted = false;
    }

    public static boolean isTestStarted() {
        return testStarted;
    }

    public static void setTestStarted(boolean testStarted) {
        CurrentTestData.testStarted = testStarted;
    }

    public static TestWindow getCurrentTestWindow() {
        return currentTestWindow;
    }

    public static void setCurrentTestWindow(TestWindow currentTestWindow) {
        CurrentTestData.currentTestWindow = currentTestWindow;
    }

    public static boolean isTestReversed() {
        return testReversed;
    }

    public static void setTestReversed(boolean testReversed) {
        CurrentTestData.testReversed = testReversed;
    }

    public static int getWordsCount() {
        return wordsCount;
    }

    public static void setWordsCount(int wordsCount) {
        CurrentTestData.wordsCount = wordsCount;
    }

    public static int getStepsCount() {
        return stepsCount;
    }

    public static void setStepsCount(int stepsCount) {
        CurrentTestData.stepsCount = stepsCount;
    }

    public static int getRestStepsCount() {
        return restStepsCount;
    }

    public static void setRestStepsCount(int restStepsCount) {
        CurrentTestData.restStepsCount = restStepsCount;
    }

    public static void decreaseStepsCount() {
        --restStepsCount;
    }

    public static int getRestWordsCount() {
        return restWordsCount;
    }

    public static void setRestWordsCount(int restWordsCount) {
        CurrentTestData.restWordsCount = restWordsCount;
    }

    public static void decreaseRestWordsCount() {
        --restWordsCount;
    }

    public static String getCurrentWord() {
        return currentWord;
    }

    public static void setCurrentWord(String currentWord) {
        CurrentTestData.currentWord = currentWord;
    }

    public static String getCurrentAnswer() {
        return currentAnswer;
    }

    public static void setCurrentAnswer(String currentAnswer) {
        CurrentTestData.currentAnswer = currentAnswer;
    }

    public static int getCorrectAnswers() {
        return correctAnswers;
    }

    public static void setCorrectAnswers(int correctAnswers) {
        CurrentTestData.correctAnswers = correctAnswers;
    }

    public static void increaseCorrectAnswers() {
        ++correctAnswers;
    }

    public static ArrayList<String> getCurrentVariants() {
        return currentVariants;
    }

    public static void setCurrentVariants(ArrayList<String> currentVariants) {
        CurrentTestData.currentVariants = currentVariants;
    }

    public static ArrayList<Translation> getCurrentTranslations() {
        return currentTranslations;
    }

    public static void setCurrentTranslations(ArrayList<Translation> currentTranslations) {
        CurrentTestData.currentTranslations = currentTranslations;
    }

    public static boolean isAnswerSubmitted() {
        return answerSubmitted;
    }

    public static void setAnswerSubmitted(boolean answerSubmitted) {
        CurrentTestData.answerSubmitted = answerSubmitted;
    }
}
