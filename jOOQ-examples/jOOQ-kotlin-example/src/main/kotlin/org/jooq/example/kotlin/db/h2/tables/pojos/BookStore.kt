/*
 * This file is generated by jOOQ.
 */
package org.jooq.example.kotlin.db.h2.tables.pojos


import java.io.Serializable


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class BookStore(
    var name: String? = null
): Serializable {


    override fun toString(): String {
        val sb = StringBuilder("BookStore (")

        sb.append(name)

        sb.append(")")
        return sb.toString()
    }
}