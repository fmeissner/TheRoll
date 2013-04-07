package com.eyeem.theroll.utils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: vishna
 * Date: 4/6/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AllColorsEver {
   public final static HashMap<String, Integer> map = new HashMap<String, Integer>();

   public static Integer name(String colorName) {
      return map.get(colorName);
   }

   static {
      map.put("beige",       0xffF4E1C1);
      map.put("black",       0xff000000);
      map.put("blue",        0xff00539C);
      map.put("brown",       0xff584039);
      map.put("dark green",  0xff12674a);
      map.put("gold",        0xffE2A829);
      map.put("grey",        0xff80817d);
      map.put("greige",      0xffA1AD92);
      map.put("green",       0xff3c824e);
      map.put("hot pink",    0xffCA628F);
      map.put("lavender",    0xff6A6378);
      map.put("light blue",  0xff7BA0C0);
      map.put("light brown", 0xffB98E68);
      map.put("light green", 0xffAFCB80);
      map.put("light grey",  0xffC5C6C7);
      map.put("light pink",  0xffDFB8B6);
      map.put("magenta",     0xff9E2C6A);
      map.put("maroon",      0xff5e081e);
      map.put("mauve",       0xff9F5069);
      map.put("navy blue",   0xff2B2E43);
      map.put("olive green", 0xff818455);
      map.put("orange",      0xffE19640);
      map.put("pink",        0xff0290B2);
      map.put("plum",        0xff582147);
      map.put("purple",      0xff87407A);
      map.put("red",         0xff9b1b30);
      map.put("teal",        0xff426972);
      map.put("turquoise",   0xff38AFCD);
      map.put("violet",      0xff473854);
      map.put("white",       0xffffffff);
      map.put("yellow",      0xffF6D155);
      map.put("skin",        0xffFCE2C4);
   }
}
