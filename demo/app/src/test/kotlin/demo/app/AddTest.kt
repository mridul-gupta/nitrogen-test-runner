package demo.app

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class AddTestMain {
    @Test
    fun testAddMain() {
        val add = Add()
        val result = add.add(2, 3)
        assertEquals(5, result)
    }
}
