package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        String[] Main_reference_paths = {"G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\astro.txt1",
                "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\astro2.txt",
        "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\block1.txt",
        "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\block2.txt",
        "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\Full.txt"};
        ScannedText astro1 = new ScannedText();
        astro1.initial_path = "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\astro1.txt";
        astro1.reference_paths=Main_reference_paths;

        ScannedText astro2 = new ScannedText();
        astro2.initial_path="G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\astro2.txt";
        astro2.reference_paths=Main_reference_paths;

        astro1.initialTables();
        astro2.initialTables();

        astro1.referenceTables();
        astro2.referenceTables();

        astro1.sortTables();
        astro2.sortTables();

        GetTextTable words = (sc) ->(sc.words);
        Float ratio = astro1.TermMatchRatio(astro2, words);//и так можно для каждой из таблиц, вызвать один и тот же метод просто передавая другую лямбду
        System.out.println("words match ratio between astro1 and astro2 is " +ratio);
    }


    static  void UnitTests(){
        boolean IO_unit_test;
        String IO_expected_result;
        {
            IO_expected_result=
                    "_____________________________________________________________________\n" +
                            "ЧАСТЬ ПЕРВАЯ.\n" +
                            "_____________________________________________________________________\n" +
                            "I.\n" +
                            "_____________________________________________________________________\n" +
                            "— Eh bien, mon prince. Gênes et Lucques ne sont plus que des apanages, des поместья, de la famille Buonaparte. Non, je vous préviens, que si vous ne me dites pas, que nous avons la guerre, si vous vous permettez encore de pallier toutes les infamies, toutes les atrocités de cet Antichrist (ma parole, j’y crois) — je ne vous connais plus, vous n’êtes plus mon ami, vous n’êtes plus мой верный раб, comme vous dites.1 Ну, здравствуйте, здравствуйте. Je vois que je vous fais peur,2 садитесь и рассказывайте.\n" +
                            "_____________________________________________________________________\n" +
                            "Так говорила в июле 1805 года известная Анна Павловна Шерер, фрейлина и приближенная императрицы Марии Феодоровны, встречая важного и чиновного князя Василия, первого приехавшего на ее вечер. Анна Павловна кашляла несколько дней, у нее был грипп, как она говорила (грипп был тогда новое слово, употреблявшееся только редкими). В записочках, разосланных утром с красным лакеем, было написано без различия во всех:\n" +
                            "_____________________________________________________________________\n" +
                            "«Si vous n’avez rien de mieux à faire, M. le comte (или mon prince), et si la perspective de passer la soirée chez une pauvre 1 Ну, князь, Генуя и Лукка поместья фамилии Бонапарте. Нет, я вам вперед говорю, если вы мне не скажете, что у нас война, если вы еще позволите себе защищать все гадости, все ужасы этого Антихриста (право, я верю, что он Антихрист), — я вас больше не знаю, вы уж не друг мой, вы уж не мой верный раб, как вы говорите.\n" +
                            "_____________________________________________________________________\n" +
                            "2 Я вижу, что я вас пугаю.\n" +
                            "_____________________________________________________________________\n" +
                            "3\n";
        }
        String path = "G:\\Programs\\CODE\\JAVA\\JavaCodes\\course_task_5\\TestText.txt";
        String test_IO = "";
        for (String s: readFile(path)) {
            test_IO+="_____________________________________________________________________\n";
            test_IO+=s+"\n";
        }

        IO_unit_test = test_IO.equals(IO_expected_result);
        if (!IO_unit_test){
            System.out.println("IO ERROR");

        } else{
            System.out.println( "IO +");
        }


    }

    static ArrayList<String> readFile(String path){
        ArrayList<String> ret = new ArrayList<String>();
        Path path1 = Paths.get(path);

        try (Stream<String> lines = Files.lines(path1)) {
            lines.forEach(s->ret.add(s));

        } catch (IOException ex) {
            System.out.println(ex);
        }
        for(int i = 0; i<ret.size(); i++)
            ret.remove("");

        return  ret;
    }
    static String[] sentencesFromFile(String path){
        String everything= "";
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }catch (IOException e){}
        return  everything.split("([.!?-])+");
    }

    static void readIntoCorpus(String filePath, Corpus c){
        ArrayList<String> file_content = readFile(filePath);
        for (String str: file_content) {
            c.count_chars(str);
        }
    } // not used
    static void readIntoCorpus(String filePath, CorpusBase c, boolean first_run){

        double test = System.nanoTime();
        System.out.println((test) +" before ");

        System.out.println(filePath);
        System.out.println(first_run);
        double billion=1E9;
        ArrayList<String> file_content = readFile(filePath);
        String entire_text = "";
        boolean test_time = true;

        System.out.println( (System.nanoTime()-test)/billion+ " after 0 ");
        test = System.nanoTime();

        for (String str: file_content) { //speed issues when trying to split into sentences
            if(test_time) entire_text+=str+" ";
            if(!test_time) c.count_cases_v2(str, first_run);
        }

        System.out.println( (System.nanoTime()-test)/billion+ " after 1 ");
        test = System.nanoTime();

        String[] sentences = entire_text.split("([.!?-])+");
        for (String str: sentences) {
            if(test_time) c.count_cases_v2(str, first_run);
        }

        System.out.println( (System.nanoTime()-test)/billion+ " after 2");
    }

    static void readIntoCorpus_v2(String filePath, CorpusBase c, boolean allow_new_keys){
        String[] sentences = sentencesFromFile(filePath);
        for (String str: sentences) {
            c.count_cases_v2(str, allow_new_keys);
        }
    } // fixed for speed
    public static void form_table_initial(ArrayList<TableRow> future_table, CorpusBase corpus1, GetMapLambda map_instruction ){
        // and lambda argument that specifies which hashMap of corpus to use
        ////////////////////////////////////////////////////////////////////////////////////
        HashMap<String, Integer> needed_map = map_instruction.getMap(corpus1);
        Iterator<HashMap.Entry<String, Integer>> it= needed_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            TableRow row = new TableRow();
            row.key = entry.getKey();
            row.initial_size = needed_map.size();
            row.initial_counter=entry.getValue();
            future_table.add(row);
        }
    }
    public static void form_table_reference(ArrayList<TableRow> future_table, CorpusBase corpus1, GetMapLambda map_instruction){
        HashMap<String, Integer> needed_map = map_instruction.getMap(corpus1);
        Iterator<HashMap.Entry<String, Integer>> it= needed_map.entrySet().iterator();
        for(int i = 0; i<future_table.size(); i++){
            TableRow row = future_table.get(i);
            if(i<future_table.size()){ row = future_table.get(i);} else{break;}
            row.reference_size = needed_map.size();
            row.reference_counter= needed_map.get(row.key);
        }
        for (TableRow row:future_table) {
            row.gen_all();
        }
    }
    //вот эти 3 метода было бы неплохо засунуть в ScannedText, и сделать чтобы они не принимали аргумент CorpusBase потому что а нафиг он нужен,  ->
    //-> там просто будет сразу указатель на поле this.innerCorpus внутри ScannedText.

}
