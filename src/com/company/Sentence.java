package com.company;

import java.util.ArrayList;

public class Sentence{

    boolean containsTerms;
    boolean checked_previously;
    int orderInText;
    ArrayList<String> rawWords;
    ArrayList<Boolean> terms;
    String getString(){
        String ret = "";
        for (int i = 0; i < rawWords.size(); i++) {
            String str = rawWords.get(i);
            if (CorpusBase.isPunctSign(str)){
                ret+= str;
            }else {
                if(terms.get(i)){
                    str = "->"+str+"<-";
                }                ret+= " "+str;
            }
        }
        return  ret;
    }
    //takes table sorted by w3 as argument, that stores words from text

    void updateTermStatus(ArrayList<TableRow> sorted_table){
        if(!checked_previously){
            resizeIfNeeded(terms, rawWords.size());
            for(int i = 0; i<250&&i<sorted_table.size(); i++){
                String term_in_question = sorted_table.get(i).key;
                for (String rawWord: rawWords) {
                    if(rawWord==term_in_question){
                        containsTerms = true;
                        int term_index = findWord(rawWord);
                        if(term_index>-1){
                            terms.add(term_index, true);
                        }
                    }
                }
            }
        }
        checked_previously=true;
    }

    Sentence(){
        containsTerms = false;
        checked_previously=false;
    }

     void resizeIfNeeded(ArrayList<Boolean> arr, int newSize){
        int original_size = arr.size();
        for(int i = 0; i<newSize- original_size; i++){
            arr.add(false);
        }
    }

     int findWord(String word){
        for(int i = 0; i<rawWords.size(); i++){
            if (rawWords.get(i) == word){
                return  i;
            }
        }
        return -1;
    }

}
