package com.example.lib
// array explaining
fun main() {
    val days = arrayOf(1, 5, 12, 23)
    days[2] = 11
    println(days[2])
    println(days.get(2))

    val daylist = mutableListOf<Int>(1, 5, 12, 23)
    daylist.add(5)
    daylist.removeAt(0)
    daylist[2] = 15
    println(daylist)
    println(daylist[2])
    println(daylist.size)

    daylist.forEach() {
        println(it)
    }
    daylist.forEachIndexed { index, i ->
        println("$index : $i")
    }

    for (i in daylist) {
        println(i)
    }
}

class MyClass {
}

fun hello() {
    println("Hello!")
}