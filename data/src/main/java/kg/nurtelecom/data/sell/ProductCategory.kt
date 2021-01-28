package kg.nurtelecom.data.sell

import java.math.BigDecimal

data class ProductCategory (
	val result : List<CatalogResult>,
	val resultCode : String,
	val details : String,
	val resultDetail : String
)

data class ProductCatalog (
	val id : Long,
	val name : String,
	val businessType : BusinessType,
	val taxPayer : TaxPayer
)

data class Products (
	val id : Long,
	val code : String,
	val name : String,
	val tnvdCode : String,
	val markTypeId : Long,
	val markCode : String,
	val price : BigDecimal,
	val unit : String
)

data class CatalogResult (
	val id : Int,
	val name : String,
	val productCatalog : ProductCatalog,
	val products : List<Products>
)

data class TaxPayer (
	val id : Long,
	val type : String,
	val inn : String,
	val personalNum : Long,
	val name : String,
	val msisdn : String,
	val email : String,
	val legalAddress : String,
	val personName : String,
	val ofdStatus : String
)

data class BusinessType (
	val id : Long,
	val gnsCode : Int,
	val description : String
)