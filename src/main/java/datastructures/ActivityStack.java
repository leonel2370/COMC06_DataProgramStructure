package datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ActivityStack {
    private Stack<String> stack = new Stack<>();
    public void push(String activity) { stack.push(activity); }
    public void display() {
        if (stack.isEmpty()) System.out.println("Aucune activité récente.");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println("Action: " + stack.get(i));
        }
    }
}
