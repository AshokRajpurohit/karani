package com.ashok.friends.nikhil;

import java.util.HashMap;

public class Class5 {

    public static void main(String[] args) {
        System.out.println("hi");
        String op = synthesize("ACBA", "{\"A\":\"B\",\"B\":\"C\",\"C\":\"D\",\"D\":\"A\"}", 2);
        System.out.println(op);
    }

    static String synthesize(String input_sequence, String synthesizer_rule, int depth) {

        String ruleLen = synthesizer_rule.substring(1, synthesizer_rule.length() - 1);
        String[] ruleMap = ruleLen.split(",");
        HashMap ruleHm = new HashMap<String, String>();
        for (String rule : ruleMap) {
            String[] rules = rule.split(":");
            ruleHm.put(rules[0], rules[1]);
        }
        String output = new String("");
        char[] ch = input_sequence.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            output = output + ruleHm.get("\"" + String.valueOf(ch[i]) + "\"");

        }
        if (depth > 1) {
            output = output.replace("\"", "");
            return synthesize(output, synthesizer_rule, depth - 1);
        }
        return output.replace("\"", "");
    }
}