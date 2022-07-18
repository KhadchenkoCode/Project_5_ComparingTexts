package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.company.Main.*; // возможно часть статических методов из Main надо засунуть сюда

public class ScannedText{

    ArrayList<TableRow> words;
    ArrayList<TableRow> wordsPunct;
    ArrayList<TableRow>[] word_groups;
    CorpusBase innerCorpus;

    String initial_path;
    String[] reference_paths;
    //create tables
    //compareToFloat ranging 0 to 1
    // 1 is all terms in top N are same
    // 0 is none
    // какие-то параметры надо подбирать тоже вручную ?

    public ScannedText(){
        this.words = new ArrayList<TableRow>();
        this.wordsPunct = new ArrayList<TableRow>();
        this.word_groups = new ArrayList[3];
        this.innerCorpus = new CorpusBase();

        for(int i = 0; i<3; i++){
            word_groups[i] = new ArrayList<TableRow>();
        }
    }

    public void initialTables(){
        readIntoCorpus_v2(initial_path, innerCorpus, true);
        form_table_initial(words, innerCorpus, (corpusBase1 -> corpusBase1.occurences_map));
        form_table_initial(wordsPunct, innerCorpus, (corpusBase1 -> corpusBase1.occurences_map_punct));
        for(int i = 0; i<3; i++){
            int finalI = i;
            form_table_initial(word_groups[i], innerCorpus, (corpusBase1 -> corpusBase1.word_groups_maps.get(finalI)));
        }

    }

    public void referenceTables(){

        for (String s: reference_paths) {
            readIntoCorpus_v2(s, innerCorpus, false);
        }
        form_table_reference(words, innerCorpus, (corpusBase1 -> corpusBase1.occurences_map));
        form_table_reference(wordsPunct, innerCorpus, (corpusBase1 -> corpusBase1.occurences_map_punct));
    }

    public void sortTables(Comparator<TableRow> comparator){
        Collections.sort(words, comparator);
        Collections.sort(wordsPunct, comparator);
        for(int i = 0; i<3; i++){
            Collections.sort(word_groups[i], comparator);
        }
    }
    public void sortTables(){
        Comparator<TableRow> w3_descending = new Comparator<TableRow>(){
            @Override
            public int compare (TableRow o1, TableRow o2) {
                int sign = 0;
                double diff = o1.w3-o2.w3;
                if(diff>0){
                    sign = -1;
                }
                if(diff<0){
                    sign  = 1;
                }
                return sign;
            }
        };
        sortTables(w3_descending);
    } //default comparator
    public ArrayList<Sentence> termSentences(){
        ArrayList<Sentence> TextSentences = new ArrayList<>();
        for (Sentence s: innerCorpus.sentences) {
            s.updateTermStatus(words);
            TextSentences.add(s);
        }
        Comparator<Sentence> order = new Comparator<Sentence>(){
            @Override
            public int compare (Sentence o1, Sentence o2) {
                return o1.orderInText   -o2.orderInText;
            }
        };
        Collections.sort(TextSentences, order);
        for(int i = 0; i< TextSentences.size(); i++){

            if(TextSentences.get(i).containsTerms){
                //System.out.println(TextSentences.get(i).getString());
            }
        }
        return TextSentences;
    }
    public float TermMatchRatio(ScannedText text, GetTextTable table_instruction){

        int wordsToCount = 1000; //тоже эмпирически подбираемый параметр как и соотношение вклада весов w1 и w2?
        //а может лучше брать топ 10% униклаьных терминов? или топ 15% ?
        ArrayList<TableRow> tableThis = table_instruction.getTable(this);
        ArrayList<TableRow> tableThat = table_instruction.getTable(text);
        float ret = 1;
        int TableSize = 0;
        if(tableThis.size()<tableThat.size()){
            TableSize=tableThis.size();
        }else {
            TableSize=tableThat.size();
        }
        int matchesCount = 0;
        System.out.println("table size = "+ TableSize);    // просто ориентировочно вывел в консоль
        for(int i = 0; i<TableSize&&i<wordsToCount; i++){ // O(N^2) беда
            TableRow thisRow = tableThis.get(i);
            for(int j = i; j<TableSize&&j<wordsToCount; j++){
                String thatKey = tableThat.get(j).key;
                if (tableThat.get(j).key.equals(thisRow.key)){
                    matchesCount++;
                    j = TableSize+1; //break
                }
            }
        }
        ret=((float)matchesCount)/wordsToCount;
        return  ret;
    }
}
