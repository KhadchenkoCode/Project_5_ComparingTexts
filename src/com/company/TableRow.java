package com.company;

import java.text.DecimalFormat;

public class TableRow {

    static final float F = 0.45f;
    static final DecimalFormat df = new DecimalFormat("0.0000000000");

    String key;
    int initial_counter;
    int initial_size;//
    double initial_ratio;

    int reference_counter;
    int reference_size;//
    double reference_ratio;

    double w1;
    double w2;
    double w3;

    public void gen_initial_ratio(){
       initial_ratio = initial_counter;
       initial_ratio = initial_ratio/initial_size;
    }

    public void gen_reference_ratio(){
        reference_ratio = reference_counter;
        reference_ratio =  reference_ratio/ reference_size;
    }

    public void generate_weights(){

        w1 = 1- initial_ratio; // if initial ratio is 0 w1 = 1; if initial ratio is 1 weight is 0
        w2 = initial_ratio-reference_ratio; //ну получается что реже
        w3 = F*w1+(1-F)*w2;
    }

    public void gen_all(){
        gen_initial_ratio();
        gen_reference_ratio();
        generate_weights();
    }

    public String  toString(){
        String ret = "[";
        String gap = "";
        for(int i = 0; (key.length()+gap.length())%35!=0; i++){
            gap+=" ";
        }
        ret+=key+gap+"\t";
        ret+=initial_counter+"\t";
        ret+=df.format(initial_ratio)+"\t";
        ret+=reference_counter+"\t";
        ret+=df.format(reference_ratio)+"\t";
        ret+=df.format(w1)+"\t"+df.format(w2)+"\t"+df.format(w3);
        ret+= "]";
        return ret;
    }

}
