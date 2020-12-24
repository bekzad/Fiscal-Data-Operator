package kg.nurtelecom.sell.utils

import kg.nurtelecom.data.sell.Product
import org.junit.Test
import org.junit.Assert.*

class TestingTest {

    @Test
    fun `fetchProductExpression$sell_debug`() {
        val fakeProduct = Product(100.0, 2.0, 200.0, null, null)
        val result = fetchProductExpression(fakeProduct)
        assertEquals(result.toString(), "100.0 * 2")
    }

    @Test
    fun fetchProductExpression_not_filled_allowance() {
        val fakeProduct = Product(100.0, 2.0, 200.0, 25.0, null)
        val result = fetchProductExpression(fakeProduct)
        assertEquals(result.toString(), "100.0 * 2 + 25% ")
    }

    @Test
    fun fetchProductExpression_not_filled_discount() {
        val fakeProduct = Product(100.0, 2.0, 200.0, null, 50.0)
        val result = fetchProductExpression(fakeProduct)
        assertEquals(result.toString(), "100.0 * 2 + 50%")
    }

    @Test
    fun fetchProductExpression_fill_all_data() {
        val fakeProduct = Product(100.0, 2.0, 200.0, 25.0, 50.0)
        val result = fetchProductExpression(fakeProduct)
        assertEquals(result.toString(), "100.0 * 2 + 25% + 50%")
    }

}