package com.example.loginapp.model;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public String id;
    public String title;
    public List<Subtopic> subtopics = new ArrayList<>();
    public List<Step> steps = new ArrayList<>();

    public static class Step {
        public String instruction;
        public List<String> substeps = new ArrayList<>();
    }
    public static class Subtopic{
        public String id;
        public String title;
        public List<Step> steps = new ArrayList<>();
    }
}
