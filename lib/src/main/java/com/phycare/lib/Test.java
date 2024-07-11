package com.phycare.lib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {

        // Anagram of String
       /* String str1 = "Bored";
        str1 = str1.toLowerCase();
        String str2 = "Robed";
        str2 = str2.toLowerCase();
        if (str1.length() == str2.length()){
            char[] str1array = str1.toCharArray();
            char[] str2array = str2.toCharArray();
            Arrays.sort(str1array);
            Arrays.sort(str2array);
            boolean result = Arrays.equals(str1array,str2array);
            if (result) {
                System.out.println("Anagrams of nums : " + result);
            }else {
                System.out.println("Anagrams of not nums : " + result);
            }
        }*/
        // ----------------------------
        // String Reverse
       /* String str = "welcome";
        char[] chars = str.toCharArray();
        for (int i=str.length()-1;i>=0; i--){
            System.out.print(chars[i]);
        }
        System.out.println("");
        StringBuffer sb = new StringBuffer(str);
        sb.reverse();
        System.out.println(sb);
        //-----  Array max num ------------------------
        int[] num = {10,20,30,40,50};
        int max = num[0];
        for (int i = 0; i < num.length; i++) {
             if (max < num[i])
            max = num[i];
        }*/
        //  System.out.println(max);

        //--------- fibonacci series --------------
      /* int n =0,n1 =1,n2;
       int num = 10;
        System.out.print(n+" "+n1);
        for (int i = 2; i < num ; i++) {
            n2 = n+n1;
            System.out.print(" "+n2);
            n=n1;
            n1 =n2;
        }*/
        // -------- palindrome -----------
        /*int num =131;
        int r,sum=0,temp;
        temp = num;
        while(num>0){
            r = num%10;
            sum=(sum*10)+r;
            num =num/10;
        }
        if (temp == sum){
            System.out.println("this is palindrome");
        }else {
            System.out.println("this is not palindrome");
        }*/
        // ---------  Anagram -------------
        /*String str1 = "Bored";
        String str2 = "Robe";
          str1 = str1.toLowerCase();
          str2 = str2.toLowerCase();
          if (str1.length() == str2.length()){
              char[] strArray1 = str1.toCharArray();
              char[] strArray2 = str2.toCharArray();
              Arrays.sort(strArray1);
              Arrays.sort(strArray2);
              boolean result = Arrays.equals(strArray1,strArray2);
              if (result){
              System.out.println("this num is anagram");}
              else {
                  System.out.println("this num is not anagram");
              }
          }*/
        // -------------------
       /*String str = "hello";
       char ch ='l';
       int count =0;
        for (int i = 0;  i < str.length() ; i++) {
            if (str.charAt(i) == ch){
                count++;
            }
        }
        System.out.println("num of occurrence "+ ch + " is "+ count);*/
        // ---- string is a palindrome ---------------
       /* String str = "racecar";
        String strRev;
        StringBuffer sb = new StringBuffer(str);
        strRev = sb.reverse().toString();
        if (str.equals(strRev)){
            System.out.println("this is palindrome");
        }else System.out.print("not palindrome");*/
     // Remove white space in a String ---------------------------
        /*String str = "hello world this is android testing ";

        str =str.replaceAll("\\s","");

        System.out.println(str);*/
       /* String str1 = "hello";
        String str2 = "world";
        System.out.println(str1+" "+str2);
        System.out.println(str1.concat(" ").concat(str2));
        String st1 = new String("hello");
        String st2 = new String("world");
        System.out.println(st1.concat(" ").concat(st2));*/
         // split a String ---------------;
        /*String str = "apple,banana,orange";
        String[] arr = str.split(",");
        for (String st: arr ) {
            System.out.println(st);
        }*/
       // Swap two Strings ---------------
       /* String a = "hello";
        String b = "world";
        a = a+b;
        b = a.substring(0,a.length() - b.length());
        a= a.substring(b.length());
        System.out.println(a+b);*/

       /* String ss = "hello";
        StringBuffer sb = new StringBuffer(ss);
        System.out.print(sb.equals(ss));*/

        // Print duplicate characters from String ---------------

        /*String str = "welcome hello";
        char[] ch = str.toCharArray();
        int count ;
        for (int i = 0; i < ch.length; i++) {
            count =1;
            for (int j = i+1; j < ch.length ; j++) {
                 if (ch[i] == ch[j] && ch[i] != ' '){
                     count ++;
                     ch[j]='0';
                 }
            }
            if (count>1 && ch[i]!='0'){
                System.out.println("count of duplicate char "+ch[i]+" = "+count);
            }
        }*/
        /*System.out.println("please enter some text");
        Scanner  scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        char[] ch = input.toCharArray();
        int count = 0;
        for (char c: ch) {
            switch (c){
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                 count++;
                 break;
                default:
                    break;
            }
        }
        System.out.println("no of: "+ count);*/

        // -----------   Reverse String worlds ----------------
        /*String str = "this is android test example";
        String[] strwords = str.split(" ");
        String revStr = "";
        for (int i = strwords.length-1; i >=0 ; i--) {
            revStr += strwords[i]+" ";
        }
        System.out.println(revStr);*/
    // ------------------               Array   ---------------------------------------------//
       /* int[] num = {2,3,4,5,6,9};
        int max = num[0];
        for (int n: num) {
               if (n>max){
                   max = n;
               }
        }
        System.out.println(max);*/
        /*int[] num = {1,2,3,4,5};
        for (int i = num.length-1; i >= 0; i--) {
              System.out.print(num[i]);
        }*/

        /*int[] num = {1,2,3,4,5};
        int n = 4;
        boolean b = false;
        int temp = 0;
        for (int i = 0; i < num.length; i++) {
               if (n==num[i]) {
                   b = true;
                   temp =num[i];
                   break;
               }
        }
        if (b) {
            System.out.println("value is = "+ temp);
        }*/
        /*int[] num = {1,2,3,4,5};
        int sum = 0;
        for (int i = 0; i < num.length; i++) {
             sum += num[i];
        }
        System.out.println(sum);*/
        // array sorting remove duplicates from an array. --------

        /*int a[]={3,2,1,4,2,1};
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                 if (a[i]>a[j]){
                     int temp = a[i];
                     a[i] = a[j];
                     a[j] = temp;
                 }
                
            }
        }
        for (int n:a) {
            System.out.print(n);
        }
        System.out.println(" ");
       int b=0;
        a[b] = a[0];
        for (int i = 0; i < a.length; i++) {
               if (a[b] != a[i]){
                   b++;
                   a[b] = a[i];
               }
        }
        for (int n:a) {
            System.out.print(n);
        }*/

        // ------ Equality Of Two Arrays In Java?

        /*int[] arrayOne = {2, 5, 1, 7, 4};

        int[] arrayTwo = {2, 5, 1, 7, 4};
        boolean bb= true;
        if (arrayOne.length == arrayTwo.length)
        {
            for (int i = 0; i < arrayOne.length; i++)
            {
                  if (arrayOne[i] != arrayTwo[i])
                  {
                      bb = false;
                  }
            }
        }

        if (bb){
            System.out.println("both are same ");
        }else {
            System.out.println("not same");
        }*/
        // --- Array duplicates
        /*int [] arr = new int [] {2, 1, 2, 3, 4, 2, 7, 8, 8, 3, 9, 3};
        for (int i = 0; i < arr.length; i++) {
            int count = 1;
            for (int j = i+1; j < arr.length ; j++) {
                   if (arr[i] == arr[j]){
                       count++;
                       System.out.println(arr[i]);
                   }
            }
        }*/
        // ========= sum of digits

      /*  int num = 252;
        int sum =0, digit;
        while(num>0){
            digit = num % 10;
            sum = sum+digit;
            num=num/10;
        }
        System.out.println(sum);*/
           // array second max number
        /*int a[]={1,2,5,6,3,2};
        int temp;
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] > a[j]) {
                     temp = a[i];
                     a[i]=a[j];
                     a[j]=temp;
                }
            }
        }
        System.out.println("Second max Num: " +a[a.length-2]);*/
        // ---------  10+8+15=33-46=13--------------------------------------------
        //-------------------------------------------------------

       /* int a[]={1,2,5,6,3,2};
        int temp;
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        int compare = 0;
        a[compare] = a[0];

        for (int i = 1; i <a.length; i++) {
            if (a[compare] != a[i]) {  // 1!=2
                compare++;
                a[compare] = a[i];
            }
        }
        for (int i : a) {

            System.out.print(i);
        }*/

     // ========   Find the Missing Number ======
       // int[] num = {1,2,3,4,6};
       // int n = num.length;
       /* int[] newOne = new int[n+1];
        for (int i = 0; i < n-1; i++) {
              newOne[num[i]]++;
        }
        for (int i = 1; i <= n; i++) {
               if(newOne[i] == 0){
                   System.out.println(i);
               }
        }*/
      /* int sum = 0;
        for (int i = 0; i < n-1; i++) {
              sum+=num[i];
        }
        int exSum = (n*(n+1))/2;
        System.out.println(exSum-sum);*/

        // ==== Leaders of array ===
        /*int arr[] = {16, 17, 4, 3, 5, 2};
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int j;
            for ( j = i+1; j < n; j++) {
                 if (arr[i]<=arr[j]){
                     break;
                 }
            }
            if (j == n){
                System.out.println(arr[i]+" ");
            }
        }*/
        // =============== Array Max and MIN =======================
        //int a[]={1,423,6,46,34,23,13,53,4};
       /* Arrays.sort(a);
        System.out.println("min: "+ a[0] +" "+"max: "+ a[a.length-1]);*/
        /*int temp;
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length ; j++) {
                if (a[i] > a[j]) {
                   temp = a[i];
                   a[i]= a[j];
                   a[j] = temp;
                }
            }
        }
        int max = a[0];
        int min = a[0];
        for (int i = 0; i < a.length ; i++) {
               if (a[i]>max){
                   max = a[i];
               }else {
                   min = a[i];
               }
        }
        System.out.println(max);
        System.out.println("Min : "+min);*/
        // ========  Reverse Array ===========================================
       /* int[] originalArr = { 1, 2, 3, 4, 5 };

        for (int i = originalArr.length-1; i >=0 ; i--) {
             System.out.print(originalArr[i]);
        }*/


      /*  String str = "welcome",rev="";
        char ch;
        for (int i=0;i<str.length(); i++){
            ch = str.charAt(i);
            rev = ch+rev;

        }

        System.out.print(rev);
        String str = "welcome";
        String rev="";
        for (int i=0;i<str.length(); i++){
            rev = str.charAt(i)+rev;
        }
        System.out.print(rev); */
        
        // === Binary Search array --------------------
        /*int a[]={1,423,6,46,34,23,13,53,4};
        Arrays.sort(a);
        for (int i:a) {
            System.out.print(i+" ");
        }
        System.out.println();
        int low =0;  // 0
        int high = a.length-1;

       // System.out.println(low); // 0
       // System.out.println(heigh);// 8

        int num = 34;
        while (low<=high) {
            int mid =(low+high)/2;
           // System.out.println(mid); // 4
            if (num == a[mid]){
                System.out.println( "search num : "+mid); // 4
                break;
            }
            if (num < a[mid]){
                high = mid - 1;
            }
            if (num > a[mid]){
                low = mid + 1;
            }
        }*/
        // Linear Search
       /* int num = 53;
        for (int i = 0; i < a.length; i++) {
            if (num == a[i] ){
                System.out.println( "search num : "+ i);
                break;
            }else {
                System.out.println( "search num : -1");
            }
        }*/

       /* List<Integer> list = Arrays.asList(1,2,4,6,8);
        int index = Collections.binarySearch(list,6);
        System.out.println( "search num index of: "+ index);*/

        /*List<Integer> list = Arrays.asList(8,6,4,2,1);
        CustomComparator comparator = new CustomComparator();
        int index = Collections.binarySearch(list,6, comparator);

        System.out.println( "search num index of: "+ index);
        int[] integers = {3, 22, 27, 47, 57, 67, 89, 91, 95, 99};
        int idex = Arrays.binarySearch(integers,91);
        System.out.println( "search num index of2    : "+ idex);
        System.out.println( "index of2    : "+ list.indexOf(4));*/

        /*List list = new ArrayList();
         list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(22);
        list.add(5);
        list.add(15);
        System.out.println( "index of 2    : "+ list.indexOf(4));
        System.out.println( "index of2    : "+ list.contains(20));*/

       /* Map<Integer,String> map = new HashMap();
        map.put(4,"Android");
        map.put(6,"Java");
        map.put(10,"Ten");
        map.put(8,"Ios");
        System.out.println("value of : "+map.get(6));
        System.out.println("value of : "+map.containsKey(10));*/

     //   String str = "hellowelcome";
       // System.out.println(str.indexOf('l')); // h
       // System.out.println(str.indexOf('e',0));  //hell
       // System.out.println(str.indexOf("o"));
        //System.out.println(str.lastIndexOf('e'));
       // System.out.println(str.lastIndexOf('l',3));
       // System.out.println(str.substring(2));
       // System.out.println(str.substring(1,4));
      //  System.out.println(str.intern());
        int[] num = {2,3,4,5,6,7};
        int first= num[0];

        if (num.length > 3) {
            for (int i = 1; i < num.length; i++) {
                if ( num[i] > first)  {
                    first = num[i];
                }
            }
          //  System.out.println(first);

            int second = num[0];
            for (int i = 0; i < num.length; i++) {
                if ( num[i] > second && num[i] < first)  {
                    second = num[i];
                }
            }
            int third = Integer.MIN_VALUE;
            for (int i = 0; i < num.length; i++) {
                if ( num[i] > third && num[i] < second)  {
                    third = num[i];

                }
            }
            System.out.printf("The third Largest " +
                    "element is %d\n", third);
        }else {
            for (int i = 1; i < num.length; i++) {
                if ( num[i] > first)  {
                    first = num[i];
                }
            }
            System.out.printf("The third Largest " +
                    "element is %d\n", first);
        }




    }
}
class CustomComparator implements Comparator<Integer>{

    @Override
    public int compare(Integer a, Integer b) {
        return b-a;
    }
}
