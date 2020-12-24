package kg.nurtelecom.sell.utils

import kg.nurtelecom.data.sell.Product


internal fun fetchProductExpression(product: Product): StringBuilder {
        val productExpressionLine = StringBuilder()
        val discount =
            if (product.discount != null && product.discount != 0.0) (" + " + product.discount?.toInt() + "% ") else
                ""
        val allowance =
            if (product.allowance != null && product.allowance != 0.0) (" + " + product.allowance?.toInt()) + "%" else
                ""

        productExpressionLine.apply {
            append("${product.price} * ")
            append("${product.count.toInt()}")
            append(discount)
            append(allowance)
        }
        return productExpressionLine
    }