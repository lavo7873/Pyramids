// package com.egyptianExample;
package com.example;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    String[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

  // print pyramid
  public void print() {
    System.out.printf("Pyramid %s\n", name);
    System.out.printf("\tid: %d\n", id);
    for (String string : contributors) {
      System.out.printf("\tContributors: %s\n", string); 
    }
  }
}
