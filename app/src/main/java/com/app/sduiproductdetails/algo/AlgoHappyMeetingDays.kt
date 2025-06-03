package com.app.sduiproductdetails.algo

fun main() {
    println(minDaysToScheduleMeetings(arrayOf(intArrayOf(0, 30), intArrayOf(5, 10), intArrayOf(15, 20))))
    println(minDaysToScheduleMeetings(arrayOf(intArrayOf(7, 10), intArrayOf(2, 4))))
    println(minDaysToScheduleMeetings(arrayOf(intArrayOf(1, 5), intArrayOf(8, 9), intArrayOf(8, 9))))
    println(minDaysToScheduleMeetings(arrayOf(intArrayOf(1, 4), intArrayOf(2, 5), intArrayOf(3, 6))))
    println(minDaysToScheduleMeetings(arrayOf(intArrayOf(1, 2), intArrayOf(2, 3), intArrayOf(3, 4), intArrayOf(4, 5))))

}
fun minDaysToScheduleMeetings(intervals: Array<IntArray>): Int {
    val events = mutableListOf<Pair<Int, Int>>()

    for (interval in intervals) {
        val (start, end) = interval
        events.add(Pair(start, 1))  // Meeting starts
        events.add(Pair(end, -1))   // Meeting ends
    }

    // Sort by time, and if equal, end (-1) comes before start (+1)
    events.sortWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })

    var currentOverlap = 0
    var maxOverlap = 0

    for ((_, eventType) in events) {
        currentOverlap += eventType
        maxOverlap = maxOf(maxOverlap, currentOverlap)
    }

    return maxOverlap
}

