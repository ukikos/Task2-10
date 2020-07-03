package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppFrame extends JFrame {
    private JPanel mainPanel;
    private JTextArea inputList;
    private JTextArea outputList;
    private JButton loadButton;
    private JButton processButton;
    private JButton saveButton;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private Border compound, raisedbevel, loweredbevel;
    private JFileChooser openJFileChooser;
    private JFileChooser saveJFileChooser;

    public AppFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        inputList.setBorder(compound);
        outputList.setBorder(compound);

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rawList = inputList.getText();                      //принимаем текст из поля ввода

                Pattern numPattern = Pattern.compile("(-?\\d+)");          //паттерн для поиска чисел (ищет комбинацию цифровых символов(число) и необязательный минус)
                Matcher numMatcher = numPattern.matcher(rawList);

                MyLinkedList<String> stringList = new MyLinkedList<>();
                MyLinkedList<Integer> intList = new MyLinkedList<>();

                while (numMatcher.find()) {
                    stringList.add(numMatcher.group());                    //добавляем найденные комбинации в лист String
                }

                 for(String item : stringList) {
                     intList.add(Integer.parseInt(item));                  //преобразуем найденные комбинации в числа Integer
                 }

                try {
                    intList.taskProcess(intList);                          //меняем пары элементов на их сумму
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                StringBuilder strBuilder = new StringBuilder();            //составляем ответ для вывода
                try {
                    strBuilder.append(intList.getFirst());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                for (int i = 1; i < intList.getSize(); i++) {
                    try {
                        strBuilder.append(" " + intList.getByIndex(i));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                String result = strBuilder.toString();

                outputList.setText(result);                                //выводим преобразованный лист в видео строки
            }
        });

        openJFileChooser = new JFileChooser();
        saveJFileChooser = new JFileChooser();

        openJFileChooser.setCurrentDirectory(new File("./src"));
        saveJFileChooser.setCurrentDirectory(new File("./src"));

        FileFilter txtFilter = new FileNameExtensionFilter("Text files (*.txt)", "txt");

        openJFileChooser.addChoosableFileFilter(txtFilter);
        saveJFileChooser.addChoosableFileFilter(txtFilter);

        saveJFileChooser.setAcceptAllFileFilterUsed(false);
        saveJFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        saveJFileChooser.setApproveButtonText("Save");


        loadButton.addActionListener(new ActionListener() {    //открытие файлов
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openJFileChooser.showOpenDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try (Scanner scan = new Scanner(openJFileChooser.getSelectedFile())) {
                        scan.useDelimiter("\\Z");
                        inputList.setText(scan.next());
                    } catch (Exception ex) {
                        outputList.setText("Error");
                    }
                }
            }
        });


        saveButton.addActionListener(new ActionListener() {    //сохранение файлов
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saveJFileChooser.showSaveDialog(AppFrame.this) == JFileChooser.APPROVE_OPTION) {
                    String filename = saveJFileChooser.getSelectedFile().getPath();
                    if (!filename.toLowerCase().endsWith(".txt")) {
                        filename += ".txt";
                    }
                    try (FileWriter wr = new FileWriter(filename)) {
                        wr.write(outputList.getText());
                        JOptionPane.showMessageDialog(AppFrame.this,
                                "Файл '" + saveJFileChooser.getSelectedFile() + "' успешно сохранен");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(AppFrame.this, "Error");
                    }
                }
            }
        });
    }
}
