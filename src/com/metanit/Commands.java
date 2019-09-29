package com.metanit;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.logging.FileHandler;

public class Commands {

    private ArrayDeque<Gamer> collection;

    public Commands(ArrayDeque<Gamer> collection){
        if (collection != null) {
            this.collection = new ArrayDeque<>(collection);
        }
        else {
            System.out.println("Пустая коллекция была создана");
            this.collection = new ArrayDeque<>();
        }
    }


    /**
     * Method removes collection's last item.
     */
    public boolean removeLast() {
        collection.removeLast();
        return true;
    }


    /**
     * If collection contains an item (argument humanToRemove), delete it.
     * @param gamerToRemove : (Human) - Object of class Human
     */
    public void remove(Gamer gamerToRemove) {
        if (collection.remove(gamerToRemove)) {
            System.out.println("Игрок успешно удален.");
        }else{ System.out.println("Этот игрок не существует в коллекции!");}

    }

    public void importAll(String path) {

        //блять надо хмл распарсить
    }

    /**
     * Method outputs all elements of the collection in string representation.
     */
    public void show() {

        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }

    }

    /**
     * Method adds item to a collection if its name value is more than the biggest-named element of this collection.
     * @param maxGamer : (Gamer) - Object of class Human
     */
    public void addIfMax(Gamer maxGamer) {
        Iterator<Gamer> iterator = collection.iterator();

        int n = iterator.next().getName().length();

        int i = 0;
        while (iterator.hasNext()) {
            i = iterator.next().getName().length();
            if (i > n) {
                n = i;
            }
        }

        if (maxGamer.getName().length() > i) {
            collection.add(maxGamer);
        }else{
            System.out.println("Имя игрока не самое большое! Игрок не был добавлен в коллекцию.");
        }
    }

    /**
     * Method shows information about storing collection.
     */

    public void info() {

        System.out.println("Коллекция имеет тип ArrayDeque  содержит объекты типа Gamer.");
        System.out.println("Сгенерированно из \"" + HavingXML.getFileName() + "\" файла.");
        System.out.println("На данный момент коллеция содержит " + collection.size() + " элементов.");

    }

    /**
     * Method adds a new item to this collection.
     * @param newGamer : (Gamer) - Object of class Gamer
     */
    public void addNew(Gamer newGamer) {
        if (collection.add(newGamer))
            newGamer.welcome();
        else System.out.println("Коллекция уже содержит этого игрока.");

    }

    /**
     * Method saves the collection to the source file based on the value of "savePermission" variable.
     * Besides, checks whether the collection is null.
     */
    public void save(){

        if (HavingXML.checkFileWrite()) {
            if (collection != null){
                HavingXML.save(collection);
            }
            else {
                System.out.println("Collection is null; Can't be saved!");
            }
        }
    }

    public void showMenu() {
        System.out.println("Введите одну из следующих команд:\n\n" + "remove_last: удалить последний элемент из коллекции\n" +
                "remove {element}: удалить элемент из коллекции по его значению\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "addIfMax {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "importAll {String path}: добавить в коллекцию все данные из файла\n" +
                "addNew {element}: добавить новый элемент в коллекцию\n" + "stop: завершить работу программы\n" +"\n" +
                "Для более подробного описания команды введите \"infoCommand(\"Название команды\")\"");
    }

    /**
     * Method prints information on commands.
     * @param command : (String) - a string with the command.
     */

    public void infoCommand(String command){

        String jsonExample = "\r\n{\r\n   \"name\": \"Winny\",\r\n   \"level\": \"6\",\r\n   \"skill\": {\r\n      \"running\": \"throws stick win a run\"},\r\n}\r";
        String pathExample; //надо встаить потом;
        switch (command){
            case "remove_last":
                System.out.println("Метод последний элемент из коллекции");
                break;
            case "remove":
                System.out.println("Если коллекция содержит элемент (аргумент команды) - метод удалит этот элемент из коллекции \n@param gamerToRemove : (Gamer) - Object of class Human.");
                System.out.println("Каждый игрок должен иметь следующие атрибуты:\n"+"\t1) NAME,\n" +"\t2) LEVEL");
                System.out.println("Skills по желанию.");
                System.out.println("Например:" + jsonExample);
                break;
            case "addIfMax":
                System.out.println("Метод добавляет элемент (аргумент команды) в коллекцию, если его значение самое большое из всех элементов, уже существующих в коллекции" +
                        "@param maxGamer : (Gamer) - Object of class Gamer written in JSON format.");
                System.out.println("Каждый игрок должен иметь следующие атрибуты:\n"+"\t1) NAME,\n" +"\t2) LEVEL");
                System.out.println("Skills по желанию.");
                System.out.println("Например:" + jsonExample);
                break;
            case "show":
                System.out.println("Метод выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
                break;
            case "info":
                System.out.println("Метод выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.");
                break;
            case "importAll":
                System.out.println("Метод добавляет в коллекцию все данные из файла, путь к которому указан в аргументе команды.");
                //System.out.println("Например:" + pathExample);
                break;
            case "addNew:":
                System.out.println("Метод добавляет новый элемент (аргумент команды) в коллекцию." +
                        "@param newGamer : (Gamer) - Object of class Gamer written in JSON format.");
                break;
            case  "stop":
                System.out.println("Заврешает работу программы.");
                break;
            default:
                System.out.println("Type \"command_name arg\" for additional info. i.e \"add arg\"");
        }
    }
}


