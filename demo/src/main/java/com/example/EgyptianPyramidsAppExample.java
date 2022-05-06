// package com.egyptianExample;
package com.example;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsAppExample {

  private Scanner scan = new Scanner(System.in);
  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;

  // Create a hash set for command 5
  HashSet<Integer> pyramidID = new HashSet<Integer>();

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  // main loop for app
  public void start() {
    // Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsAppExample() {
    // read egyptian pharaohs
    String pharaohFile =
      "C:/Users/huy/GitHub/NassefEgyptianPyramidsApp/demo/src/main/java/com/example/pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile =
      "C:/Users/huy/GitHub/NassefEgyptianPyramidsApp/demo/src/main/java/com/example/pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);

  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharoah to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

    // initialize the pyramid array
    private void initializePyramid(JSONArray pyramidJSONArray) {
      // create array and hash map
      pyramidArray = new Pyramid[pyramidJSONArray.size()];
  
      // initalize the array
      for (int i = 0; i < pyramidJSONArray.size(); i++) {
        // get the object
        JSONObject o = (JSONObject) pyramidJSONArray.get(i);
  
        // parse the json object
        Integer id = toInteger(o, "id");
        String name = o.get("name").toString();
        JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
        String[] contributors = new String[contributorsJSONArray.size()];
        for (int j = 0; j < contributorsJSONArray.size(); j++) {
          String c = contributorsJSONArray.get(j).toString();
          contributors[j] = c;
        }
  
        // add a new pyramid to array
        Pyramid p = new Pyramid(id, name, contributors);
        pyramidArray[i] = p;
      }
    }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // print all pharaohs (command 1)
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  // print all pyramids (command 3)
  private void printAllPyramid() {
    for (int i = 0; i < pyramidArray.length; i++) {
      printMenuLine();
      printPyramid(i);
      printMenuLine();
    }
  }

  // print a specific pharaoh (command 2)
  private void printSpecificPharaoh() { 
    String id;
    System.out.print("Enter a Pharaoh id: ");
    id = scan.nextLine();
    Integer identity = Integer.parseInt(id);
    for (int i = 0; i < pharaohArray.length; i++) {
      if (pharaohArray[i].id == identity) {
        printMenuLine();
        pharaohArray[i].print();
        printMenuLine();
        break;
      }
    }
    // if the id is not in the array
    // System.out.println("We don't have a Pharaoh for that id");
    // scan.nextLine();
  }

  // print a specific pyramid (command 4)
  private void printSpecificPyramid() { 
    String id;
    System.out.print("Enter a Pyramid id: ");
    id = scan.nextLine();
    Integer identity = Integer.parseInt(id);
    for (int i = 0; i < pyramidArray.length; i++) {
      if (pharaohArray[i].id == identity) {
        printMenuLine();
        printPyramid(i);
        printMenuLine();
        // Add requested pyramids to the hash set
        pyramidID.add(identity);
        break;
      }
    }
  }

  // print the requested pyramids (no duplicates)
  private void printRequestedPyramid() {
    printMenuLine();
    System.out.println("List of requested Pyramids:");
    System.out.printf("\tId\tName\n");
    System.out.printf("\t---\t-----------------\n");
    for (Integer i : pyramidID) {
      System.out.printf("\t%d\t%s\n", i, pyramidArray[i].name);
    }
    printMenuLine();
  }

  // pyramids printing function for command 3 & 4
  private void printPyramid(int id) {
    Integer totalContribution = 0;
    int count = 0;
    System.out.printf("Pyramid %s\n", pyramidArray[id].name);
    System.out.printf("\tid: %d\n", pyramidArray[id].id);
    for (String contributor : pyramidArray[id].contributors) {
      for (int i = 0; i < pharaohArray.length; i++) {
        if (contributor.contentEquals(pharaohArray[i].hieroglyphic)) {
          count++;
          System.out.printf("\tContributtor %d: %s %d gold coins\n", count, pharaohArray[i].name, pharaohArray[i].contribution);
          totalContribution += pharaohArray[i].contribution;
        }
      }
    }
    System.out.printf("\tTotal contribution: %d gold coins\n", totalContribution);
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        printSpecificPharaoh();
        break;
      case '3':
        printAllPyramid();
        break;
      case '4':
        printSpecificPyramid();
        break;
      case '5':
        printRequestedPyramid();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
      "--------------------------------------------------------------------------"
    );
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharoahs");
    printMenuCommand('2', "Display a specific Egyptian Pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Display a specific pyramid");
    printMenuCommand('5', "Display a list of requested pyramids");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
}
