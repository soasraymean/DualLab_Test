package com.dualLab;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String file_path = in.nextLine();
        try (FileReader fileReader = new FileReader(new File(file_path))) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<BusInfo> rawTimetable = new ArrayList<>();

            String line = bufferedReader.readLine();
            while (line != null) {
                if (getTravelTimeFromInput(line) <= 60) {
                    rawTimetable.add(new BusInfo(line));
                }
                line = bufferedReader.readLine();
            }

            modifyTimetable(rawTimetable);
            printResult(rawTimetable);

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResult(List<BusInfo> list) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));

            if (list.size() == 0) {
                bufferedWriter.write("");
                bufferedWriter.close();
                return;
            }

            int index = 0;
            while (index < list.size()) {
                if ("Posh".equals(list.get(index).getCompanyName())) {
                    bufferedWriter.append(list.get(index).toString()).append('\n');
                }
                index++;
            }
            index = 0;
            do {
                if ("Grotty".equals(list.get(index).getCompanyName())) {
                    bufferedWriter.append('\n').append(list.get(index).toString());
                }
                index++;
            } while (index < list.size());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyTimetable(List<BusInfo> list) {
        if (list.size() <= 1) return;
        Collections.sort(list);

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getDepartureTimeInMinutes() == list.get(i + 1).getDepartureTimeInMinutes() && list.get(i).getArrivalTimeInMinutes() == list.get(i + 1).getArrivalTimeInMinutes()) {
                if ("Grotty".equals(list.get(i).getCompanyName())) {
                    list.remove(i);
                    i--;
                } else {
                    list.remove(i + 1);
                }
            }
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getArrivalTimeInMinutes() == list.get(i + 1).getArrivalTimeInMinutes()) {
                if (list.get(i).getDepartureTimeInMinutes() < list.get(i + 1).getDepartureTimeInMinutes()) {
                    list.remove(i);
                    i--;
                } else {
                    list.remove(i + 1);
                }
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getDepartureTimeInMinutes() == list.get(i + 1).getDepartureTimeInMinutes()) {
                if (list.get(i).getArrivalTimeInMinutes() > list.get(i + 1).getArrivalTimeInMinutes()) {
                    list.remove(i);
                    i--;
                } else {
                    list.remove(i + 1);
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) continue;
                if (list.get(i).getArrivalTimeInMinutes() > list.get(j).getArrivalTimeInMinutes()) {
                    if (list.get(i).getArrivalTimeInMinutes() - getTravelTime(list.get(i).getDepartureTimeInMinutes(), list.get(i).getArrivalTimeInMinutes()) <
                            list.get(j).getArrivalTimeInMinutes() - getTravelTime(list.get(j).getDepartureTimeInMinutes(), list.get(j).getArrivalTimeInMinutes())) {
                        list.remove(i);
                        i--;
                        if (i < 0) i = 0;
                    }
                } else if (list.get(i).getArrivalTimeInMinutes() < list.get(j).getArrivalTimeInMinutes()) {
                    if (list.get(i).getArrivalTimeInMinutes() - getTravelTime(list.get(i).getDepartureTimeInMinutes(), list.get(i).getArrivalTimeInMinutes()) >
                            list.get(j).getArrivalTimeInMinutes() - getTravelTime(list.get(j).getDepartureTimeInMinutes(), list.get(j).getArrivalTimeInMinutes())) {
                        list.remove(j);
                        j--;
                        if (j < 0) j = 0;
                    }
                }
            }
        }

    }

    private static int getTravelTime(int departureTime, int arrivalTime) {
        int travelTime = arrivalTime - departureTime;
        if (travelTime < 0) travelTime += 24 * 60;
        return travelTime;
    }

    private static int getTravelTimeFromInput(String str) {
        int travelTime = getTimeFromInputInMinutes(str.substring(str.lastIndexOf(" ") + 1)) - getTimeFromInputInMinutes(str.substring(str.indexOf(" ") + 1, str.lastIndexOf(" ")));
        if (travelTime < 0) travelTime += 24 * 60;
        return travelTime;
    }

    private static int getTimeFromInputInMinutes(String str) {
        return Integer.parseInt(str.substring(0, 2)) * 60 + Integer.parseInt(str.substring(3));
    }

}