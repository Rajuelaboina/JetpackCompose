package com.phycare.lib

class Sample {
}
fun main(){

    //doSomthing(10,20){x,y-> println(x+y) }
   // doSomthing(5,5){x,y-> println(x-y) }
   // doSomthing(20,30){x,y->x * y}
   //val s = doSomthing(20){x,-> println(x) }
  //  println(s)
    trickOrTreat(true){x-> "Welcome" }
}
fun doSomthing(x:Int,fuction:(Int)-> Unit): ()-> Int{
       fuction(x)
       return { 20+30 }
}
fun trickOrTreat(isTrick: Boolean, extraTreat: (Int) -> String): () -> Unit {
    val trick = {
        println("No treats!")
    }

    val treat = {
        println("Have a treat!")
    }
    if (isTrick) {
        return trick
    } else {
        println(extraTreat(5))
        return treat
    }
}