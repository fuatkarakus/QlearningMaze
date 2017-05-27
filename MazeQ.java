package mazeq;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class MazeQ {
     
   static   ArrayList<ArrayList<Integer>> input = new ArrayList<>();

 Scanner start= new Scanner(System.in);
     static int dest;
     static int iterator;
     static int baslangic;
     static int currentState=0;
     static  double Gamma=0.8;
     static int initalize_states[] =initalize_state_olustur(input); // başlangıç düğümlerini atıyor.
     static int Qsize = initalize_states.length;
      static int Rmatris[][]=R_olustur(dest); //r matrisini oluşturuyor.
     static int Qmatris[][]=Q_zero(); // q matrisini sıfır yapıyor.
     
    public static void main(String[] args) {
        Scanner start= new Scanner(System.in);
        
        System.out.print("baslangic durumu girin:");
        baslangic=start.nextInt();
       System.out.print("hedef durumu girin:");
       dest=start.nextInt();
        System.out.print("Iterator sayısını girin:");
        iterator=start.nextInt();
        
       oku(); // txt verilerini okur inputa atar.
       
      
       
    }
    static void train(int[] initalize_states){
        for (int j = 0; j < iterator; j++) {
            for (int i = 0; i < input.size(); i++) {
                episode(initalize_states[i]);
            }//i
        }//j
        System.out.println("Q matris değerleri:");
        for (int i = 0; i < Qsize; i++) {
            for (int j = 0; j < Qsize; j++) {
                System.out.print(Qmatris[i][j] + ",\t");
            }//j
            System.out.print("\n");
        }//i
        System.out.print("\n");
        return;
    }
    static void test(){
        System.out.println("En kısa yollar");
        for (int i = 0; i < Qsize; i++) {
            currentState = initalize_states[i];
            int newState = 0;
            do
            {
               newState = maximum(currentState, true); 
                System.out.println(currentState + ",");
                currentState= newState;
            }while(currentState < dest);
            System.out.print(dest+"\n");
        }
    }
    static int[] initalize_state_olustur(ArrayList input){
            int durumSayisi=input.size();
            int[] initalize_state= new int [durumSayisi];
            for (int i = 0; i < durumSayisi; i++) {
                initalize_state[i]=i;
        }
        
        
        return initalize_state;        
    }
    static void episode(int initalize_states) {
       //throw new UnsupportedOperationException("Not supported yet."); 
        for (int i = 0; i < input.size(); i++) {
            currentState=initalize_states;
        
       
        do{
            chooseAnAction();
        }while(currentState==dest);
      
         for (int j = 0; j <input.size(); j++) {
             chooseAnAction();
         }
         }
        
    }
    static void chooseAnAction(){
         int possibleAction=0;
         possibleAction=getRandomAction(input.size());
         if(Rmatris[currentState][possibleAction]>=0){
             Qmatris[currentState][possibleAction]=reward(possibleAction);
             currentState=possibleAction;
         }
     }
    static int getRandomAction( int upperBound){//rasgele bir başlangıç seçiyor
         int action=0;
         boolean choiceIsValid=false;
         while(choiceIsValid==false){
             action=new Random().nextInt(upperBound);
             if(Rmatris[currentState][action]>-1){
                 choiceIsValid=true;
             }
         }
         return action;
     }
    static int maximum(int State, boolean ReturnIndexOnly){
         int winner=0;
         boolean foundNewWinner=false;
         boolean done=false;
         while(!done)
         {
             foundNewWinner=false;
             for (int i = 0; i <input.size(); i++) {
                 if(i != winner){
                     if(Qmatris[State][i] > Qmatris[State][winner]){
                         winner=i;
                         foundNewWinner=true;
                     }
                 }
             }
             if(foundNewWinner==false){
                 done=true;
             }
         }
         if(ReturnIndexOnly==true){
             return winner;
         }
         else{
             return Qmatris[State][winner];
         }
         
     }
    static int reward( int Action){
         return (int)(Rmatris[currentState][Action]+(Gamma*maximum(dest, false)));
     }
   static void oku(){
        String satir;
        try {
            BufferedReader dosya = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\ornekGirdiler\\input22.txt"));

            while ((satir = dosya.readLine()) != null) {
                String[] elemanlar = satir.split(",");
                 ArrayList<Integer> komsu = new ArrayList<>();
                for (int i = 0; i < elemanlar.length; i++) {
                    komsu.add(Integer.parseInt(elemanlar[i]));
                }
                input.add(komsu);
            }
		}
		catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        
        
        
    
        
    }
   //static int bune = input.get(dest).size();
   static int [][] R_olustur(int s){
     
              
    for (int i = 0; i <input.size(); i++) {
            for (int j = 0; j <input.size(); j++) {
                Rmatris[i][j]=-1;
                
            }
           
        }
        for (int i = 0; i <input.size(); i++) {
            for (int j = 0; j <input.get(i).size(); j++) {
                Rmatris[input.get(i).get(j)][i]=0;
                
            }
        }
        for (int i = 0; i <input.get(dest).size(); i++) {
            
                Rmatris[input.get(dest).size()][dest]=100;
            
        }
        for (int i = 0; i <input.size(); i++) {
            if(i==dest)
                Rmatris[i][i]=100;
        }
        
        for (int i = 0; i <input.size(); i++) {
            //System.out.println("\n");
            for (int j = 0; j <input.size(); j++) {
                
                System.out.print(+Rmatris[i][j]+" , ");
                
            }
            System.out.println();
        }
    return Rmatris;
}
   static int [][] Q_zero(){
    int[][] Q = new int[input.size()][input.size()];
     for(int i = 0; i <input.size(); i++)
        {
            for(int j = 0; j <input.size(); j++)
            {
                Q[i][j] = 0;
            } // j
        } // i
      
    return Q;
 }   
 

}