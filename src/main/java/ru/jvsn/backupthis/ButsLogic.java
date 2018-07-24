/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jvsn.backupthis;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;

/**
 *
 * @author proxz
 */
public class ButsLogic {

    private static long timeOfTh = 0; // Переменная для подсчета секунд (времени действия архивации)
    private boolean trigger; // Переключатель
    private boolean firstRunTask = true; // Переключатель для остановки скрипта если он уже выполнялся сегодня (что бы в один день таск не выполнялся много раз)

    // Главный метод который занимается архивацией
    public void ZipFolder(String getFolder, String setZipFolder, int setDay, int btnNumber, boolean t) {

        // Создаем таймер
        Timer timer = new Timer();
        trigger = t;

        // Создаем таск
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (trigger) {
                    boolean setStart = getDay(setDay); // Получаем результат сравнения выбранного дня и сегодняшнего
                    if (setStart && firstRunTask) {

                        // Костыль для динамического отображения состояния скрипта
                        if (btnNumber == 1) {
                            ButsGUI.jLabel14.setText(setStatus(1));
                        } else if (btnNumber == 2) {
                            ButsGUI.jLabel15.setText(setStatus(1));
                        } else if (btnNumber == 3) {
                            ButsGUI.jLabel16.setText(setStatus(1));
                        }
                        // Костыль для динамического отображения состояния скрипта

                        // Заносим в переменную время в мс в начале старта скрипта
                        long startTime = System.currentTimeMillis();

                        try {
                            // Запуск архивации сторонней библиотекой
                            ZipUtil.pack(new File(getFolder), new File(setZipFolder + getNameToBackupFile()));

                            // Заносим в переменную время в мс в конце выполнения скрипта и после вычитаем для того что бы получить время выполнения
                            timeOfTh = System.currentTimeMillis() - startTime;

                            // На всякий случай логируем выполнение
                            ButsLogger.LoggerMe("Task done! Time spend: " + msToHms(timeOfTh) + "s");
                            firstRunTask = false; // Переключатель от повторов
                        } catch (ZipException ex) {
                            JOptionPane.showMessageDialog(null, "Папки либо файла не существует!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            ButsLogger.LoggerMe(ex.toString());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                            ButsLogger.LoggerMe(ex.toString());
                        }

                    } else {

                        // Костыль для динамического отображения состояния скрипта
                        if (btnNumber == 1) {
                            ButsGUI.jLabel14.setText(setStatus(2));
                        } else if (btnNumber == 2) {
                            ButsGUI.jLabel15.setText(setStatus(2));
                        } else if (btnNumber == 3) {
                            ButsGUI.jLabel16.setText(setStatus(2));
                        }
                        // Костыль для динамического отображения состояния скрипта

                        //DEBUG
                        System.out.println("wait...");
                        //DEBUG

                        // Рандомный таймер для того что бы потоки не перехватывали выполнение друг друга
                        // Рандомим от 1й секунды до 10 минут
                        //int c = 1000 + (int) (Math.random() * 60000) * 10;
                        long c = 1080000; // Спим 3 часа...
                        try {
                            Thread.sleep(c);
                        } catch (InterruptedException ex) {
                            ButsLogger.LoggerMe(ex.toString());
                        }
                    }
                } else {

                    // Костыль для динамического отображения состояния скрипта
                    if (btnNumber == 1) {
                        ButsGUI.jLabel14.setText(setStatus(0));
                        ButsGUI.jLabel4.setText("Следующий запуск...");
                    } else if (btnNumber == 2) {
                        ButsGUI.jLabel15.setText(setStatus(0));
                        ButsGUI.jLabel8.setText("Следующий запуск...");
                    } else if (btnNumber == 3) {
                        ButsGUI.jLabel16.setText(setStatus(0));
                        ButsGUI.jLabel12.setText("Следующий запуск...");
                    }
                    // Костыль для динамического отображения состояния скрипта

                    trigger = false;
                    cancel(); // Останавливаем таск если кнопка отжата

                    //DEBUG
                    System.out.println("stop");
                    //DEBUG
                }
            }
        }, 0, 3 * 10000); // Таск будет чекаться каждые 30сек
    }

    // Метод переводящий MS to HMS
    public String msToHms(long ms) {
        String result = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(ms),
                TimeUnit.MILLISECONDS.toMinutes(ms)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms)),
                TimeUnit.MILLISECONDS.toSeconds(ms)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));

        return result;
    }

    // Метод для получения строки статус, хз зачем он, после будет убран :)))
    public String setStatus(int stCount) {
        String status = "";

        switch (stCount) {
            case 0:
                status = "Остановлено";
                break;
            case 1:
                status = "В процессе";
                break;
            case 2:
                status = "В ожидании";
                break;
        }

        return status;
    }

    // Метод проверяющий дату и выбранный день в настройках
    private boolean getDay(int selectDay) {
        // Java 8 is hear :)
        int currentDay = LocalDate.now().getDayOfWeek().getValue();
        selectDay += 1;

        //DEBUG
        System.out.println("selectDay: " + selectDay + " currentDay: " + currentDay);
        //DEBUG

        if (currentDay == selectDay) {
            return true;
        } else {
            firstRunTask = true;
            return false;
        }
    }

    // Возвращаем дату когда будет сделан бэкап и делаем из него название архива
    private String getNameToBackupFile() {
        DateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
        Date today = Calendar.getInstance().getTime();
        return dt.format(today) + ".zip";
    }
}
