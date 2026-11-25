package Collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        
        list.add("Claude Code");
        list.add("Codex");
        list.add("Antigravity");

        System.out.println("Coding Agents:");
        
        for (String lang : list) {
            System.out.println(lang);
        }

        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(5);

        for (Integer numInteger : set) {
            IO.println(numInteger);
        }

    }    
}
