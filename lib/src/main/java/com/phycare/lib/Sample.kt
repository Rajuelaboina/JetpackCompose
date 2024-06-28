package com.phycare.lib

fun main(){
   sum(10,20){a,b -> a+b}
    even2(32){a-> if (a%2==0) println("this is Even number: $a") else println("this is Odd Num: $a") }

    caluc(10,20){ x, y-> println("addition of value:  ${x+y}")}
    caluc(20,30){ x, y-> println("sub of value:  ${x-y}") }
    caluc(5,6){ x, y-> println("mul of value:  ${x*y}") }
    caluc(5,6,::add2)
}
fun add(x:Int,y:Int)= println( "addd: ${x+y}")
fun add2(x:Int,y:Int){
    println(x+y)
}
fun sum(x:Int,y:Int,ope:(Int,Int)->Unit){
         ope(x,y)
}
fun sub(x:Int,y:Int,userFun:(Int,Int)->Unit){
    userFun(x,y)
}
fun even2(a:Int,operation:(Int)->Unit){
       val result = operation(a)

}
fun caluc(x:Int, y:Int, operation:(Int, Int) -> Unit){
    operation(x,y)
}
// onPause() onStop() onDestroy()
//onPause() onStop(),