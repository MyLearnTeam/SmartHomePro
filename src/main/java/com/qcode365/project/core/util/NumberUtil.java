package com.qcode365.project.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
   
   private static boolean isMatch(String regex, String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       Pattern pattern = Pattern.compile(regex);
       Matcher isNum = pattern.matcher(orginal);
       return isNum.matches();
   }

   public static boolean isPositiveInteger(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
   }

   public static boolean isNegativeInteger(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^-[1-9]\\d*", orginal);
   }

   public static boolean isWholeNumber(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
   }

   public static boolean isPositiveDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
   }

   public static boolean isNegativeDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
   }

   public static boolean isDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
   }

   public static boolean isRealNumber(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isWholeNumber(orginal) || isDecimal(orginal);
   }
}

