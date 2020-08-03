package KanBan.Logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InsertionSort {
    public static void sort(List<Note> notes) {
        int n = notes.size();
        for (int i = 1; i < n; ++i) {
            Note key = notes.get(i);
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && notes.get(j).getDueDate().compareTo(key.getDueDate()) > 0) {
                notes.set(j+1,notes.get(j));
                j = j - 1;
            }
            notes.set(j+1, key);
        }
    }
}
