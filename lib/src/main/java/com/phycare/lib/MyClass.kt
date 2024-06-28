package com.phycare.lib

class MyClass {
}
fun main(){
   /* val sum: (Int) -> Unit = { s:Int-> println(s) }
    sum(10)
    val even: (Int) -> Unit = {even:Int -> if (even%2==0) println("this is $even even num") else println("this is $even odd num") }
    even(3)

    evenNum(5) { even: Int -> if (even % 2 == 0) println("this is $even even num") else println("this is $even odd num") }
   // val addition:(Int,Int)->Unit = {a:Int,b:Int -> println(a+b) }
   // addition(10,20)
    additionNum(5,6){a,b -> println(a+b) }
    additionNum2(10,20) { a, b -> a + b }*/

   /* itemClicked(20,30,object : OnClick{
        override fun onClicked(num: Int) {
            println("this is interface fun $num")
        }
    }){a,b->a+b}*/

   // val value:(Int,Int)->Unit = {a:Int,b:Int-> println("sum of a+b =  $a+b") }
   // value2(10,20){a,b-> println(a+b) }
   // evenNum(10){num-> if (num%2==0) println("this $num is even") else println("this $num is odd") }
   // even(5){a-> a%2==0}
  //  custom(even(2){a-> a%2==0})
  //  val sun:(Int,Int)-> Int = {a,b -> a+b}
  //  val sub= {a:Int,b:Int -> a-b}
  //  sub(8,9)
    //val vvv ={a:Int-> println( a%2==0)}
    vvv(2){a:Int-> println( a%2==0)}

}

fun vvv(i: Int, function: (Int) -> Unit) {
    function(i)

}


fun custom(even: Unit) {
    even(even)
}

fun even(ee: Unit) {
    println(ee)
}

fun even(a:Int,ee:(Int)-> Boolean){
    val vv = ee(a)
    if (vv){
        println("$a num is Even")
    }else{
        println("$a num is odd")
    }
}
fun evenNum(a:Int,even:(Int)->Unit){
    even(a)
}
fun value2(a:Int,b:Int,sum:(Int,Int)->Unit){
    sum(a,b)
}
fun doSomething(){

}












/*
fun itemClicked(a:Int,b:Int,action: OnClick,add:(Int,Int)->Int){
    add(a,b)
    action.onClicked(a+b)
}
fun additionNum2(a:Int,b:Int,add:(Int,Int) -> Int){
    val num = add(a,b)
    println("sum of a+b= $num")
}
fun additionNum(a:Int,b:Int, add:(Int,Int)->Unit){
     add(a,b)
}
fun evenNum(num:Int,function:(Int)->Unit){
    function(num)
}
interface OnClick {
    fun onClicked(num: Int)
}*/
