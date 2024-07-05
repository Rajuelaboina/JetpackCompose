package com.phycare.lib

import java.util.Arrays

class Examples {
    constructor(a:Int,b:Int){
        println(a)
        println(b)
    }
}
fun main(){
    
    val s = "Hello"
    val s1 = "World"
    //println("$s $s1")

   // println(s.plus(s1))
   // println(s+s1)
  // val name: String? = null
   // println(name!!)

   // val sum = {a:Int,b:Int -> a+b}

    /*calculator(10,20,::addi)
    calculator(30,10,::subs)
    calculator(10,10){x,y -> x*y}*/
    val list = mutableSetOf("Android","Java","kotlin","Ios","Android")
    val list2 = setOf("A","B","c","A","B")
   // println(list)
  //  println(list2)
  //  list.distinct()
    // distinct
    // toSet
    //HashSet()
    // mutablesetOF
    //set
    val array = arrayOf("10","20","30","20","10")

    array.forEach {
        println(it)
    }

   // val distinct = array.distinct()

  //  println(array.toSet())
   // println(array.toMutableSet())
    Arrays.sort(array)
    println(array.toHashSet())
}
fun addi(x:Int,y:Int) = x+y
fun subs(x:Int,y:Int):Int{
    return x-y
}
fun calculator(a:Int,b:Int,operation:(Int,Int)->Int){
   val result = operation(a,b)
    println(result)
}
class DataClass(id: Int, name:String,age:Int)
