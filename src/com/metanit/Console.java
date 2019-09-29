 package com.metanit;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private Commands commands;
    private boolean needExit;
    Scanner myScan = new Scanner(System.in);

    public Console() throws Exception {
        if(HavingXML.checkFileRead()) {
            this.commands = new Commands(HavingXML.convertToArrayDeque());
            needExit = false;
            this.execute();
        }
        else{
            System.out.println("hehehe");
            needExit = true;
        }
    }

    public void execute(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println();
                if (HavingXML.getSavePermission()) {
                    commands.save();
                }else {
                    System.err.println("********* Fatal Quit: Lost collection data! **********");
                }

            } catch (Exception e) {
                System.err.println("********* Fatal Quit: Lost collection data! **********");
            }
        }));

        while (!needExit) {
            String[] fullCommand;
            String typeOfInput = whichTypeOfInput();
            if (typeOfInput.equals("1")) {
                fullCommand = readOneLinedCommands();
            } else if (typeOfInput.equals("exit")) {
                fullCommand = new String[1];
                fullCommand[0] = "exit";
            } else {
                fullCommand = readMultipleLinedCommand();
            }
            try {

                Gamer forAction = null;
                if (fullCommand[0].equals("addNew") || fullCommand[0].equals("remove")
                        || fullCommand[0].equals("addIfMax")) {
                    if (fullCommand.length == 1) {
                        System.out.println("Ошибка, " + fullCommand[0] + " должна сожержать аргумент.");
                        System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
                        continue;
                    }

                    if ((fullCommand.length == 2) && !fullCommand[1].equals("arg")) {
                        try {
                            Gson gson = new Gson();
                            forAction = gson.fromJson(fullCommand[1], Temp.class).createGamer();

                            if ((forAction == null) || (forAction.getName() == null) || (forAction.getLevel() == null)) {
                                System.out.println("Error, the item is set incorrectly: \n- You may not have specified all the values!");
                                System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
                                continue;
                            }
                        } catch (JsonSyntaxException ex) {
                            System.out.println("Error, the item is set incorrectly!\nType \"command_name arg\" for additional info. i.e \"add arg\"");
                            continue;
                        }
                    } else if (fullCommand[1].equals("arg")) {
                        commands.infoCommand(fullCommand[0]);
                        continue;
                    }


                }

                switch (fullCommand[0]) {
                    case "info":
                        commands.info();
                        break;
                    case "show":
                        commands.show();
                        break;
                    case "showMenu":
                        commands.showMenu();
                        break;
                    case "removeLast":
                        commands.removeLast();
                    case "addNew":
                        commands.addNew(forAction);
                        break;
                    case "addIfMax":
                        commands.addIfMax(forAction);
                        break;
                    case "importAll":
                        //commands.importAll(path);
                        break;
                    case "remove":
                        commands.remove(forAction);
                        break;
                    case "save":
                        if (!HavingXML.getSavePermission()) {
                            promptSave();
                            if (!HavingXML.getSavePermission()) {
                                commands.save();
                            }
                        } else {
                            System.err.println("********* Fatal Quit: Lost collection data! **********");
                        }
                        break;
                    case "exit":
                        needExit = true;
                        break;


                    default:
                        System.out.println("Error: undefined command!");
                }
            } catch (Exception e) {
                System.out.println("The command is set improperly!");
                continue;
            }

        }
    }

    /**
     * Method reads One Lined JSON Commands commands from console.
     * @return String[] with a list of commands, split by spaces.
     */
   private String[] readOneLinedCommands(){
        String command;
        try {
            System.out.println();
            System.out.println("Feed me with your command:");
            command = myScan.nextLine();
            System.out.println();

        }catch(NoSuchElementException | IllegalStateException ex){
            command = "exit";
        }
        command = command.trim();
        String[] fullCommand = command.split(" ",2);
        if(fullCommand.length > 1) {
            while (fullCommand[1].contains("  ")) {
                fullCommand[1] = fullCommand[1].replaceAll("  ", " ");
            }
        }
        return fullCommand;
    }

    /**
     * Method reads Multiple Lined JSON Commands commands from console.
     * @return String[] with a list of commands, split by spaces.
     */
    private String[] readMultipleLinedCommand(){

        String myInput;
        String[] fullCommand = new String[2];
        StringBuffer s = new StringBuffer();
        String[] temp;
        int count = 0;


        try {
            System.out.println();
            System.out.println("Feed me with your command:");
            fullCommand[0] = myScan.nextLine();

        }
        catch(NoSuchElementException ex){
            fullCommand[0] = "exit";
        }

        try {

            if (fullCommand[0].equals("show") || fullCommand[0].equals("info") || fullCommand[0].equals("save")) {
                return fullCommand;
            }

            if(fullCommand[0].contains("{")){
                count++;
                temp = fullCommand[0].split(" ");
                fullCommand[0] = temp[0];
                s.append("{");

                if (temp[1].contains("}")) {
                    s.append("}");
                    return fullCommand;
                }
            }
            while (myScan.hasNext()) {

                myInput = myScan.nextLine();
                myInput = myInput.replaceAll("\t", "");
                myInput = myInput.replaceAll("\n", "");
                s.append(myInput);

                if (myInput.equals("{") || myInput.substring(myInput.length() - 1).trim().equals("{")) {
                    count++;
                }
                if (myInput.equals("}") || myInput.contains("},") || myInput.substring(myInput.length() - 1).trim().equals("}")) {
                    count--;
                }
                if (myInput.contains("}") && count == 0) {
                    break;
                } else if (count < 0) {
                    break;
                }
            }
            System.out.println();
            fullCommand[1] = s.toString();

        }
        catch (Exception e){}
        return fullCommand;
    }

    /**
     * Method asks for the format of the JSON input command.
     * By default "One Lined JSON" format is chosen.
     * @return String with a type of a command ("1" or "2").
     */
    private String whichTypeOfInput(){
        String type = "1";
        String temp;
        try {
            System.out.println();
            System.out.println("Введите номер желаемого формата ввода:");
            System.out.println("\t1) Однострочный JSON");
            System.out.println("\t2) Многострочный JSON");
            System.out.println("По усмоланию выбрана опция \"однострочный JSON.\"");
            temp = myScan.nextLine().trim();
            if (temp.length() == 1 || temp.equals("1") || temp.equals("2")){
                type = temp;
            }else {
                System.out.println("\nВаш выбор превосходит наши возможности!\nпроверьте написание в следующий раз.\nТеперь вы используете опцию по умолчанию.");
            }


        }catch(NoSuchElementException | IllegalStateException ex){
            type = "exit";
        }

        type = type.trim();

        return type;
    }

    /**
     * Method asks whether to overwrite a source file when the import was initially broken.
     * (It changes the private variable "savePermission" if allowed to.
     */
    public void promptSave(){
        try {

            String command;
            System.out.println();
            System.out.println("Вы хотите сохранить вашу коллекцию в файл?");
            System.out.println("\tДа/Нет");
            System.out.println("Будьте внимательны: \"Да\" подрузамевает перезапись исходного файла!");
            System.out.println("По умолчанию выбрано: \"No\"");
            command = myScan.nextLine();
            command = command.trim();

            if(command.contains("Да") || command.contains("да")) {
                HavingXML.setSavePermission(true);
            }

        }catch(Exception ex){
            System.out.println("Что то пошло не так");
            System.out.println("Ваш файл не будет перезаписан!");
        }
    }

}
