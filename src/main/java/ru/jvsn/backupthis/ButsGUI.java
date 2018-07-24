/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jvsn.backupthis;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Frame.NORMAL;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author proxz
 */
public class ButsGUI extends javax.swing.JFrame {

    // Создаем наши экземпляры
    public ButsLogic createButs1 = new ButsLogic();
    public ButsLogic createButs2 = new ButsLogic();
    public ButsLogic createButs3 = new ButsLogic();
    private final String TMP_DIR = System.getenv("APPDATA") + "\\BackUpThisTMP\\"; // Путь к временной папке 
    private final String TMP_FILE_NAME = TMP_DIR + "settings.cfg"; // Название файла с настройками

    /**
     * Creates new form ButsGUI
     */
    public ButsGUI() {
        initComponents();
        setTitle("BackUpThis'S");
        setVisible(true);
        setLocationRelativeTo(null);

        // Проверка на возможность работы с треем
        if (!SystemTray.isSupported()) {
            ButsLogger.LoggerMe("System tray is not supported!");
            return;
        }

        // Прописываем иконку в трее, картинку к ней, название и т.п.
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/1.gif"));
        TrayIcon trayIcon = new TrayIcon(image, "BackUpThis'S");
        // Вешаем на иконку слушателя для нажатия мышки
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // При нажатии мышки по иконке в трее - возвращать форму в прежнее состояние
                setExtendedState(NORMAL);
                setAlwaysOnTop(true);
                setVisible(true);
            }
        });

        // Ресайз иконки
        trayIcon.setImageAutoSize(true);

        // Сам процесс добавления иконки в трей
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            ButsLogger.LoggerMe(ex.toString());
        }

        // Достаем настройки и заполняем ими jTextField и прочее.
        setStartedJlabel();

        // Прописываем иконку в заголовке программы
        ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png")); //
        setIconImage(icon.getImage());

        // Скрываем время следующего запуска
        jLabel4.setEnabled(false);
        jLabel12.setEnabled(false);
        jLabel8.setEnabled(false);

        // Вешаем слушателя на окно формы для управления его состоянием
        this.addWindowListener(new WindowAdapter() {
            // Проверка перед закрытием окна (для записи настроек)
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(rootPane, "Закрыть программу?", "Выход из программы", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    saveSettings(); // Сохраняем настройки
                    ButsLogger.LoggerMe("Программа закрыта!"); // Логируем закрытие на всякий случай
                    System.exit(0); // Закрываем программу
                }
            }

            // Если окно сворачивается то физически оно скрывается
            @Override
            public void windowIconified(WindowEvent e) {
                setVisible(false);
            }
        });

    }

    // Метод который получает и задает дату следующего запуска
    private void setNextStart(int btnSel, int btnId) {

        Calendar cal = Calendar.getInstance();
        int c = 0;
        String dayDesc = "";
        btnSel += 1;

        switch (btnSel) {
            case 1:
                c = Calendar.MONDAY;
                dayDesc = "Понедельник";
                break;
            case 2:
                c = Calendar.TUESDAY;
                dayDesc = "Вторник";
                break;
            case 3:
                c = Calendar.WEDNESDAY;
                dayDesc = "Среда";
                break;
            case 4:
                c = Calendar.THURSDAY;
                dayDesc = "Четверг";
                break;
            case 5:
                c = Calendar.FRIDAY;
                dayDesc = "Пятница";
                break;
            case 6:
                c = Calendar.SATURDAY;
                dayDesc = "Суббота";
                break;
            case 7:
                c = Calendar.SUNDAY;
                dayDesc = "Воскресенье";
                break;
        }

        while (cal.get(Calendar.DAY_OF_WEEK) != c) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }

        DateFormat dt = new SimpleDateFormat("dd.MM.yyyy");

        if (btnId == 1) {
            jLabel4.setText("Следующий запуск - " + dayDesc + " (" + dt.format(cal.getTime()) + ")");
        }
        if (btnId == 2) {
            jLabel8.setText("Следующий запуск - " + dayDesc + " (" + dt.format(cal.getTime()) + ")");
        }
        if (btnId == 3) {
            jLabel12.setText("Следующий запуск - " + dayDesc + " (" + dt.format(cal.getTime()) + ")");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jTextField1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField1.setText("D:\\test\\");
            jTextField1.setToolTipText("Введите путь к файлу либо папке. Слэш на конце ОБЯЗАТЕЛЕН!");

            jTextField2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
            jTextField2.setText("D:\\");

                jLabel1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel1.setText("Откуда:");

                jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel2.setText("Куда:");

                jComboBox1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье" }));

                jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel3.setText("Каждые:");
                jLabel3.setToolTipText("");

                jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel4.setText("Следующий запуск...");

                jTextField3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jTextField3.setToolTipText("Введите путь к файлу либо папке. Слэш на конце ОБЯЗАТЕЛЕН!");

                jTextField4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

                jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel5.setText("Откуда:");

                jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel6.setText("Куда:");

                jComboBox2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье" }));

                jLabel7.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel7.setText("Каждые:");

                jLabel8.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel8.setText("Следующий запуск...");

                jTextField5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jTextField5.setToolTipText("Введите путь к файлу либо папке. Слэш на конце ОБЯЗАТЕЛЕН!");

                jTextField6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

                jLabel9.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel9.setText("Откуда:");

                jLabel10.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel10.setText("Куда:");

                jComboBox3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье" }));

                jLabel11.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel11.setText("Каждые:");

                jToggleButton3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                jToggleButton3.setText("Запуск");
                jToggleButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jToggleButton3.setFocusPainted(false);
                jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jToggleButton3ActionPerformed(evt);
                    }
                });

                jToggleButton2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                jToggleButton2.setText("Запуск");
                jToggleButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jToggleButton2.setFocusPainted(false);
                jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jToggleButton2ActionPerformed(evt);
                    }
                });

                jToggleButton1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                jToggleButton1.setText("Запуск");
                jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jToggleButton1.setFocusPainted(false);
                jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jToggleButton1ActionPerformed(evt);
                    }
                });

                jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
                jLabel12.setText("Следующий запуск...");

                jLabel13.setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
                jLabel13.setForeground(new java.awt.Color(153, 153, 153));
                jLabel13.setText("ver. 1.0.5");

                jLabel14.setFont(new java.awt.Font("Calibri", 0, 9)); // NOI18N
                jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel14.setText("Остановлено");
                jLabel14.setToolTipText("");

                jLabel15.setFont(new java.awt.Font("Calibri", 0, 9)); // NOI18N
                jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel15.setText("Остановлено");

                jLabel16.setFont(new java.awt.Font("Calibri", 0, 9)); // NOI18N
                jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel16.setText("Остановлено");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel6))
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel9))
                                    .addComponent(jLabel10))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField6)
                                            .addComponent(jTextField5)
                                            .addComponent(jTextField4)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField1)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox1, 0, 117, Short.MAX_VALUE)
                                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel11)))
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jToggleButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel13)))
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel15))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jToggleButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // Обработка нажатия кнопок
        setNextStart(jComboBox2.getSelectedIndex(), 2);
        if (jToggleButton2.isSelected()) {
            jToggleButton2.setFont(new java.awt.Font("Calibri", 1, 14));
            jToggleButton2.setText("Стоп");
            jComboBox2.setEnabled(false); // Блокируем дни (защита от дурака)
            jTextField3.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            jTextField4.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            createButs2.ZipFolder(jTextField3.getText(), jTextField4.getText(), jComboBox2.getSelectedIndex(), 2, true);
        } else {
            jToggleButton2.setFont(new java.awt.Font("Calibri", 0, 14));
            jToggleButton2.setText("Запуск");
            jComboBox2.setEnabled(true); // Разлачиваем выбор дней
            jTextField3.setEditable(true); // Разлачиваем ТФ для редактирования 
            jTextField4.setEditable(true); // Разлачиваем ТФ для редактирования 
            createButs2.ZipFolder(null, null, 0, 2, false);
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // Обработка нажатия кнопок
        setNextStart(jComboBox1.getSelectedIndex(), 1);
        if (jToggleButton1.isSelected()) {
            jToggleButton1.setFont(new java.awt.Font("Calibri", 1, 14));
            jToggleButton1.setText("Стоп");
            jComboBox1.setEnabled(false); // Блокируем дни (защита от дурака)
            jTextField1.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            jTextField2.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            createButs1.ZipFolder(jTextField1.getText(), jTextField2.getText(), jComboBox1.getSelectedIndex(), 1, true);
        } else {
            jToggleButton1.setFont(new java.awt.Font("Calibri", 0, 14));
            jToggleButton1.setText("Запуск");
            jComboBox1.setEnabled(true); // Разлачиваем выбор дней
            jTextField1.setEditable(true); // Разлачиваем ТФ для редактирования 
            jTextField2.setEditable(true); // Разлачиваем ТФ для редактирования 
            createButs1.ZipFolder(null, null, 0, 1, false);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // Обработка нажатия кнопок
        setNextStart(jComboBox3.getSelectedIndex(), 3);
        if (jToggleButton3.isSelected()) {
            jToggleButton3.setFont(new java.awt.Font("Calibri", 1, 14));
            jToggleButton3.setText("Стоп");
            jComboBox3.setEnabled(false); // Блокируем дни (защита от дурака)
            jTextField5.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            jTextField6.setEditable(false); // Блокируем ТФ для редактирования пока выполяется таск
            createButs3.ZipFolder(jTextField5.getText(), jTextField6.getText(), jComboBox3.getSelectedIndex(), 3, true);
        } else {
            jToggleButton3.setFont(new java.awt.Font("Calibri", 0, 14));
            jToggleButton3.setText("Запуск");
            jComboBox3.setEnabled(true); // Разлачиваем выбор дней
            jTextField5.setEditable(true); // Разлачиваем ТФ для редактирования 
            jTextField6.setEditable(true); // Разлачиваем ТФ для редактирования 
            createButs3.ZipFolder(null, null, 0, 3, false);
        }
    }//GEN-LAST:event_jToggleButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public static javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    // End of variables declaration//GEN-END:variables

    // Метод сохранения настроек в файл
    private void saveSettings() {

        // Локальные переменные для вывода
        String str1 = "";
        String str2 = "";
        String str3 = "";

        try {

            // Проверяем, если временной папки нет, создаем
            File createTmpFolder = new File(TMP_DIR);
            if (!createTmpFolder.exists()) {
                createTmpFolder.mkdir();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(TMP_FILE_NAME, false));

            // Костыльная проверка на пустые поля
            str1 += jTextField1.getText().isEmpty() ? "0!" : jTextField1.getText() + "!";
            str1 += jTextField2.getText().isEmpty() ? "0!" : jTextField2.getText() + "!";

            str2 += jTextField3.getText().isEmpty() ? "0!" : jTextField3.getText() + "!";
            str2 += jTextField4.getText().isEmpty() ? "0!" : jTextField4.getText() + "!";

            str3 += jTextField5.getText().isEmpty() ? "0!" : jTextField5.getText() + "!";
            str3 += jTextField6.getText().isEmpty() ? "0!" : jTextField6.getText() + "!";

            // Записываем всё наше добро в файлик
            bw.write(str1 + jComboBox1.getSelectedIndex() + "!");
            bw.newLine();
            bw.write(str2 + jComboBox2.getSelectedIndex() + "!");
            bw.newLine();
            bw.write(str3 + jComboBox3.getSelectedIndex() + "!");
            bw.newLine();

            bw.close(); // Закрываем поток
        } catch (Exception ex) {
            ButsLogger.LoggerMe(ex.toString());
        }

    }

    // Метод достающий настройки из файла и заполняющий элементы
    private void setStartedJlabel() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(TMP_FILE_NAME));
            String line;
            String str = "";
            ArrayList<String> arr = new ArrayList<>();
            while ((line = bf.readLine()) != null) {
                str += line;
            }

            String[] mass = str.split("!");

            jTextField1.setText(mass[0]);
            jTextField2.setText(mass[1]);
            jComboBox1.setSelectedIndex(Integer.parseInt(mass[2]));

            jTextField3.setText(mass[3]);
            jTextField4.setText(mass[4]);
            jComboBox2.setSelectedIndex(Integer.parseInt(mass[5]));

            jTextField5.setText(mass[6]);
            jTextField6.setText(mass[7]);
            jComboBox3.setSelectedIndex(Integer.parseInt(mass[8]));
        } catch (Exception ex) {
            ButsLogger.LoggerMe(ex.toString());
        }
    }
}
