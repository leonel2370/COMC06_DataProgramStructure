package datastructures;

import java.util.ArrayList;
import java.util.List;

class BorrowingHistory {
    private HistoryNode head;
    public void addLog(String log) {
        HistoryNode newNode = new HistoryNode(log);
        newNode.next = head;
        head = newNode;
    }
    public void display() {
        HistoryNode current = head;
        if (current == null) System.out.println("Aucun historique.");
        while (current != null) {
            System.out.println("- " + current.entry);
            current = current.next;
        }
    }
}
