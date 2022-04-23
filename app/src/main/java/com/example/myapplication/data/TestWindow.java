package com.example.myapplication.data;

public enum TestWindow {
    START(0), FIRST_TEST(1), SECOND_TEST(2), FINISH(3);

    private int value;
    TestWindow(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
