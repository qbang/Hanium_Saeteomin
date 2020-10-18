package com.example.hanium_saeteomin.boardfragment;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeConverter {
    public String toFormat(String createdAt) {
        long timeNow = System.currentTimeMillis();
        Date date = new Date(timeNow);

        String dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        String currentYear = dateFormat.split("-")[0].substring(0, 4);
        String createdYear = createdAt.split("-")[0].substring(0, 4);

        String currentMonth = dateFormat.split("-")[1].substring(0, 2);
        String createdMonth = createdAt.split("-")[1].substring(0, 2);

        String currentDay = dateFormat.split("-")[2].substring(0, 2);
        String createdDay = createdAt.split("-")[2].substring(0, 2);

        String[] currentHourMin = dateFormat.split(" ")[1].split(":");
        String[] createdHourMin = createdAt.split(" ")[1].split(":");
        System.out.println(currentHourMin[0] + currentHourMin[1] + currentHourMin[2]);
        System.out.println(createdHourMin[0] + createdHourMin[1] + createdHourMin[2]);

        String convertedDate;
        String day;
        String hourMin;

//      1년 이상 차이나면 그냥 created year로
        if (Integer.parseInt(currentYear) - Integer.parseInt(createdYear) >= 1) {
            convertedDate = createdYear + "/" + createdMonth + "/" + createdDay;
        } else { //1달이상 차이
            if (Integer.parseInt(currentMonth) - Integer.parseInt(createdMonth) >= 1) {
                convertedDate = createdMonth + "/" + createdDay;
                if (createdMonth.startsWith("0")) {
                    convertedDate.substring(0, 1);
                }

            } else { //1일이상 차이
                if (Integer.parseInt(currentDay) - Integer.parseInt(createdDay) >= 1) {
                    convertedDate = createdMonth + "/" + createdDay;
                    if (createdMonth.startsWith("0")) {
                        convertedDate.substring(0, 1);
                    }
                } else { //1일 안쪽
                    //1시간 이상 차이
                    if (Integer.parseInt(currentHourMin[0]) - Integer.parseInt(createdHourMin[0]) > 1) {
                        convertedDate = createdHourMin[0] + ":" + createdHourMin[1];
                    } else if (Integer.parseInt(currentHourMin[0]) - Integer.parseInt(createdHourMin[0]) == 1) {
                        if (Integer.parseInt(currentHourMin[1]) > Integer.parseInt(createdHourMin[1])) {
                            convertedDate = createdHourMin[0] + ":" + createdHourMin[1];
                        } else {
                            convertedDate = (Integer.parseInt(currentHourMin[1]) + 59
                                    - Integer.parseInt(createdHourMin[1])) + "분 전";
                        }
                    } else {
                        if (Integer.parseInt(currentHourMin[1]) - Integer.parseInt(createdHourMin[1]) >= 1) {
                            convertedDate = (Integer.parseInt(currentHourMin[1]) - Integer.parseInt(createdHourMin[1]))
                                    + "분 전";
                        } else {
                            convertedDate = "방금";
                        }
                    }
                }
            }
        }
        return convertedDate;
    }
}
