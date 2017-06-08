package com.arpg.utils;

public class StringUtils{
  
  private StringUtils(){}

  public static boolean isBlank(String str){
    return str == null || str.trim().length() == 0;
  }

  public static String trimLeft(String source){
    if(isBlank(source)) return null;

    int index = 0;
    for(; index < source.length(); index++){
      if(source.charAt(index) > 32) break;
    }
    
    return source.substring(index);
  }
  
  public static String trimRight(String source){
    if(isBlank(source)) return null;

    int index = source.length()-1;
    for(; index >=0; index--){
      if(source.charAt(index) > 32) break;
    }
    
    return source.substring(0, index+1);
  }

}
