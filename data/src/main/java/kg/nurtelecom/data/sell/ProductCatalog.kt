package kg.nurtelecom.data.sell


data class ProductCatalog (
    val id : Long,
    val name : String,
    val businessType : BusinessType,
    val taxPayer : TaxPayer
)