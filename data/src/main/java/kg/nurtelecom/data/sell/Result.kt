package kg.nurtelecom.data.sell


data class Result (
    val id : Int,
    val name : String,
    val productCatalog : ProductCatalog,
    val products : List<Products>
)