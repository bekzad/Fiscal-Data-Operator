package kg.nurtelecom.data.history

data class Pageable (
	val sort : Sort,
	val pageNumber : Int,
	val pageSize : Int,
	val offset : Int,
	val unpaged : Boolean,
	val paged : Boolean
)