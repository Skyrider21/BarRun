import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Objects;
//import java.util.Random;


class BarRun
{
    //counter
    static String MyName = "";
    static int counter = 5, a = 0;
    static int[] countdown = {5, 4, 3, 2, 1};
    static float last_chance= 0.9f;

    //Money
    static float myMoney = 51.0f;
    static float[] beerPrices = {1.0f, 4.0f, 6.0f,8.0f};
    static int entranceFee = 20;
    static ArrayList<String> historyList  = new ArrayList<>();
    static String drinks;
    static float beerPrice;

    //player values
    static int health = 100;
    static int days = 0;
    static int alcoholLevel = 0;

    //modes
    static boolean maniac = false;
    static String pastAction = "";
    static int backDownToEarthJudgement = 0;

    //difficulty
    static String difficulty;
    static int difficultyLevel;

    //console input
    static BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {

        //name input
        System.out.println("How would you like to be called?");
        MyName = reader.readLine();

        //timer
        while (counter > 0) {
            System.out.println(countdown[a]);
            Thread.sleep(1000);

            if (a == 4) {
                System.out.println(last_chance);

                Thread.sleep(1000);
            }
            a++;
            counter--;
        }

        System.out.println("Welcome to the party " + MyName +"!!!\n");
        calculateMoney(entranceFee,"   Entrance Fee" );

        //difficulty
        System.out.println("\n\n\n\n\n\n\n\n\nChoose the difficulty\n vodka: 1 \n beer: 2 \n cocktails: 3 ");
        System.out.println(BarRun.chooseDifficulty());
        System.out.println("Your current balance is: " + myMoney + "€");

        //game loop
        while (health > 0){

          //checks before the next day starts
          if(backDownToEarthJudgement == 3 + 3-difficultyLevel){
              myMoney /= 2;
              health /= 2;
              pastAction = pastAction + "\n PICK ONE IDIOT";
              backDownToEarthJudgement = 0;
          }
          if(myMoney < 0) {
              health = health - 20;
              pastAction = "YOU ARE STARVING!!!";
          }

          //choice menu
          System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
          System.out.println("DAY: "+days);
          System.out.println("HEALTH: "+health);
          System.out.println("MONEY: "+myMoney);
          System.out.println("\n" + pastAction);
          System.out.println("\nwould you like to: \n Buy a "+ difficulty + ": 'b'\n Flip a coin with the bartender: 'f' \n Wash dishes for money: 'w' \n Ask the pretty girl next to you for her number: 'a'");
          String selection = reader.readLine();

          //actions choice menu
          if (Objects.equals(selection, "b")) {
              System.out.println("\nBuy a "+difficulty);
              calculateMoney(beerPrice, "   " + difficulty );

              health = health + 5;
              alcoholLevel = alcoholLevel + 1;
              pastAction = "You bought a "+ difficulty;
          }
          else if (Objects.equals(selection, "f")){
              System.out.println("\nFlip a coin");
              if(maniac)
              {
                  pastAction = "Ohhhh you lost again!!!";
                  myMoney = myMoney -5;
              }
              if(randomNumber(50))
              {
                  pastAction = "Haha too easy!!!";
                  myMoney = myMoney +5;
              }
              else
              {
                  pastAction =":/ fair game..";
                  myMoney = myMoney -5;
              }

          }
          else if (Objects.equals(selection, "w")) {
              System.out.println("\nWash dishes");

              if (alcoholLevel > 0){
                  alcoholLevel = alcoholLevel -1;
              }

              health = health - 20;
              myMoney = myMoney + 10 - alcoholLevel; //5-10
              pastAction = "you washed succesfully starting to feel better";
          }
          else if (Objects.equals(selection, "a")) {
              System.out.println("\nAsk for number");

              if(maniac)
              {
                  pastAction = "Calm down crazy guy";
                  myMoney -= 2;
              }

              else if(randomNumber(80))
              {
                  pastAction = "you got it congrats!";
                  health = health + 20;
                  backDownToEarthJudgement +=1;
                  alcoholLevel -=1;

              }
              else {
                  health = health -5;
                  pastAction = "Sorry :( not this time you should drink more...";
              }



          }
          else continue; // <--- jumps back to the beginning of the while loop

          //checks at the end of the day
          for(int i = 3; i > 0; i--) {
              Thread.sleep(400); //<--- sleep for a short time --> animation of the dots
              System.out.print(".");
            }
            if (alcoholLevel >= 5) {
                maniac = true;
            }
            maniac = false;

            //end day
            health--;
            days++;
        }

        //prints buy history
        for (String s : historyList) {
            System.out.println(s);
        }
        System.out.println("---------------------\n" + myMoney +"€");
        System.out.println("GAME OVER!!! YOU SURVIVED "+days+" DAYS");

    }
    static void calculateMoney(float price, String item)
    {
        myMoney = myMoney - price;
        historyList.add(myMoney + " -" + price + "€" + item);
    }
    static float chooseDifficulty() throws IOException {

        drinks  = reader.readLine();
        int intDrinks = Integer.parseInt(drinks);

        switch (intDrinks) {
            case 1 -> {
                beerPrice = beerPrices[1];
                difficulty = "vodka";
                difficultyLevel = 1;
                calculateMoney(beerPrices[0], "   Easy mode I see" );
            }
            case 2 -> {
                beerPrice = beerPrices[2];
                difficulty = "beer";
                difficultyLevel = 2;
                calculateMoney(beerPrices[0], "   Basic mode" );
            }
            case 3 -> {
                beerPrice = beerPrices[3];
                difficulty = "cocktail";
                difficultyLevel = 3;
                calculateMoney(beerPrices[0], "   Tryhard mode" );
            }
            default -> chooseDifficulty();
        }

        

        
        return intDrinks * beerPrices[0];
    }
    static boolean randomNumber(float pos)
    {
        int min = 0;
        int max = 99;
        float random_int = (int)Math.floor(Math.random() * (max - min + 1) + min); //chooses random number between 0 & 99

        if(random_int*alcoholLevel*0.1 + random_int > pos)
            return true;
        return false;
    }
}