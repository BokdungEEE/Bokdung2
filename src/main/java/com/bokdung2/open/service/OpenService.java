package com.bokdung2.open.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OpenService {
    private boolean previousResponse = false;
    public boolean isServiceOpen() {
        previousResponse = !previousResponse;
        return previousResponse;
    }
}
