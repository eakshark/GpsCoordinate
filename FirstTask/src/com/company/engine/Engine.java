package com.company.engine;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Aleksandar on 11/11/2016.
 */
public interface Engine  {
    void run() throws IOException, JSONException;
}
