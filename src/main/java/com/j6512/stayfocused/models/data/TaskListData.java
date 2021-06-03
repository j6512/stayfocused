package com.j6512.stayfocused.models.data;

import com.j6512.stayfocused.models.TaskList;

import java.util.ArrayList;

public class TaskListData {

    public static ArrayList<TaskList> findByColumnAndValue(String column, String value, Iterable<TaskList> allTaskLists) {

        ArrayList<TaskList> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")) {
            results = findByValue(value, allTaskLists);

            return results;
        }

        for (TaskList taskList : allTaskLists) {

            String aValue = getFieldValue(taskList, column);

            if (aValue != null && column.equals("list")) {
//                if (aValue.equals(value)) {
//                    results.add(taskList);
//                }
                if (aValue.contains(value)) {
                    results.add(taskList);
                }
            } else if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(taskList);
            }
        }

        return results;
    }

    public static String getFieldValue(TaskList taskList, String fieldName) {

        String theValue = "";

        if (fieldName.equals("list")) {
            theValue = taskList.getName();
        }

        return theValue;
    }

    public static ArrayList<TaskList> findByValue(String value, Iterable<TaskList> allTaskLists) {
        String lower_val = value.toLowerCase();

        ArrayList<TaskList> results = new ArrayList<>();

        for (TaskList taskList : allTaskLists) {
            if (taskList.getName().toLowerCase().contains(lower_val)) {
                results.add(taskList);
            }
        }

        return results;
    }
}
