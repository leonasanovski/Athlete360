export interface Page<T>{
  content: T[],
  pageable: Pageable,
  last: boolean,
  totalElements: number,
  totalPages: number,
  first: boolean,
  numberOfElements: number,
  size: number,
  number: number,
  sort: Sort,
  empty: boolean
}

export interface Sort{
  sorted: boolean,
  unsorted: boolean,
  empty: boolean
}

export interface Pageable{
  pageNumber: number,
  pageSize: number,
  sort: Sort,
  offset: number,
  paged: boolean,
  unpaged: boolean
}
