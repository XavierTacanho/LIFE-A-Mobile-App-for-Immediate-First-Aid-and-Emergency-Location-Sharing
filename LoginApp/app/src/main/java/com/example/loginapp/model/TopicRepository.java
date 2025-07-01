package com.example.loginapp.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class TopicRepository {

    private static List<Topic> topicList;

    // Inicializa: carrega o JSON do assets só 1 vez
    public static void init(Context context) {
        if (topicList != null) return;

        try {
            InputStream is = context.getAssets().open("topics.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Topic>>(){}.getType();
            topicList = gson.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
            topicList = null;
        }
    }

    // Devolve a lista completa
    public static List<Topic> getAllTopics() {
        return topicList;
    }

    // Devolve um tópico pelo ID
    public static Topic findById(String id) {
        if (topicList == null) return null;
        for (Topic t : topicList) {
            if (t.id.equals(id)) return t;
        }
        return null;
    }
}
