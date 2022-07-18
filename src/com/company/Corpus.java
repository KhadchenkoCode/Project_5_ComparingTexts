package com.company;
import java.lang.reflect.GenericArrayType;
import java.util.*;
import java.util.regex.Pattern;

public class Corpus{

    public HashMap<String, Integer> char_occurences_map;
    int key_sum;


    String[] split_string(String start) {
        //потом удалим знаки препинания

        return start.split("([*^\\|«\\[\\]»/()$.—,!?:;'\\\"-]|\\s)+");
        // return  start.split( " ");
    }


    void count_chars(String arg){// additive

        String[] words_in_line = split_string(arg);

        for(int i = 0; i<words_in_line.length; i++){
            String key_char_str = words_in_line[i];
            //    key_char_str = key_char_str.toLowerCase();
            boolean contains_key_arg  = char_occurences_map.containsKey(key_char_str);
            if(contains_key_arg){

                int old_value = char_occurences_map.get(key_char_str);
                char_occurences_map.put(key_char_str, old_value+1);
                key_sum++;

            }else{
                key_sum++;
                char_occurences_map.put(key_char_str, 1);
            }
        }
    }
    Corpus(){
        this.char_occurences_map= new HashMap<String, Integer>();
        key_sum = 0;
    }



}



/*
               int tmp = char_occurences.get(i);
               char_occurences.set(i, tmp+1);
               //char_occurences[i]++;
               char_occurences.set(i, char_occurences.get(i)+1);
                int one_higher = char_occurences.get(i-1);
                if(one_higher<=tmp){



                }

 */


/*
    void sort_chars_list(Comparator<Map.Entry<String, Integer>>  arg){

        for (Map.Entry<String, Integer> entry : char_occurences_map.entrySet()) {

            char_list.add(entry);
            //    ret += "[" + s +" " + char_occurences_map.get(s) +"] ";
        }

        Collections.sort(char_list, arg);
    }
     void sort_chars_list(){

        Comparator<Map.Entry<String, Integer>> descending_sort =new Comparator<Map.Entry<String, Integer>>(){
            @Override
            public int compare (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        };
        sort_chars_list(descending_sort);
    }


*/





/*
    void count_chars(String arg){// additive



        for(int i = 0; i<arg.length(); i++){

            String key_char_str = ""+arg.charAt(i);

            boolean contains_key_arg  = char_occurences_map.containsKey(key_char_str);
            if(contains_key_arg){

                int old_value = char_occurences_map.get(key_char_str);
                char_occurences_map.put(key_char_str, old_value+1);
                key_sum++;

            }else{
                key_sum++;
                char_occurences_map.put(key_char_str, 1);
            }

        }

    }
    */