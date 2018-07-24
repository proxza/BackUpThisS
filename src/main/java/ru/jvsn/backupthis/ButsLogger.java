/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jvsn.backupthis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author proxz
 */
public class ButsLogger {

    private static String DIR_TO_LOG = new File("").getAbsolutePath() + "\\bslog.log";

    // Метод ловящий ошибки и кидающих их в лог файл :)
    public static void LoggerMe(String errorMsg) {

        try {

            DateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();

            BufferedWriter bw = new BufferedWriter(new FileWriter(DIR_TO_LOG, true));
            bw.write(dt.format(today) + " " + errorMsg);
            bw.newLine();
            bw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
