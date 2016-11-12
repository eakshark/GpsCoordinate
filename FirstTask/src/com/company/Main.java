package com.company;

import com.company.engine.Engine;
import com.company.engine.EngineImpl;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Aleksandar on 11/11/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, JSONException {
        Engine engine = new EngineImpl();
        engine.run();
    }
}
