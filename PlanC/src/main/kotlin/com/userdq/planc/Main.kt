package com.userdq.planc

import com.userdq.planc.parser.Parser
import java.net.ServerSocket

fun main(args: Array<String>) {
    ServerSocket(args.first().toInt()).let {
        while (true)
            Parser.parser(it.accept())
    }
}