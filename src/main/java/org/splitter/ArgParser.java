package org.splitter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ArgParser {
    String[] strings;
    Map<String, Boolean> keysList = new HashMap<String, Boolean>();
    Map<String, List<String>> parsedArgs = new HashMap <String, List<String>> ();
    ArgParser (String[] args) {
        strings = args;
    }
    void addKey (String key, Boolean isArgReqiured) {
        keysList.put(key, isArgReqiured);
    }
    void parse () {
        parsedArgs.put("", new LinkedList<>());
        int i = 0;
        while(i < strings.length)
        {
            String s = strings[i];
            if (keysList.containsKey(s)){
                List<String> v = new LinkedList<>();
                if (keysList.get(s)) {
                    if (i+1 < strings.length) {
                        String ns = strings[i+1];
                        if (keysList.containsKey(ns)) {
                            System.err.println("Warning: key "+s+" contains key "+ns+" as value.");
                        } 
                        v.add(strings[i+1]);
                        i = i+1;
                    } else {
                        System.err.println("Warning: key "+s+" doesn't contain value. Using empty value");
                    }    
                }
                parsedArgs.put(s, v);
                i = i+1;
            } else {
                parsedArgs.get("").add(s);
                i = i+1;
            }
        }
    }
    List<String> get (String key)
    {
        return parsedArgs.get(key);
    }
}
