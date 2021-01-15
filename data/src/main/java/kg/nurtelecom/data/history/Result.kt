package kg.nurtelecom.data.history

data class Result (
	val content : List<Content>,
	val pageable : Pageable,
	val last : Boolean,
	val totalPages : Int,
	val totalElements : Int,
	val first : Boolean,
	val sort : Sort,
	val numberOfElements : Int,
	val size : Int,
	val number : Int,
	val empty : Boolean
)