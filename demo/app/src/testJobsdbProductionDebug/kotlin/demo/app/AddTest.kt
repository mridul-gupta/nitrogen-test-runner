package demo.app

import org.junit.Assert.*
import org.junit.Test

class AddTestJdb {
    @Test
    fun testAddJdb() {
        val add = Add()
        val result = add.add(2, 3)
        assertEquals(5, result)
    }
}
