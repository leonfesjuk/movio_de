export interface Cinema {
  id: string;
  name: string;
  address: string;
  cityName: string;
}

export interface CinemaCreateDto {
  name: string;
  address: string;
  webLink: string;
  geonameId: number;
}

export interface CinemaListResponse {
  items: Cinema[];
  pagination: {
    hasMore: boolean;
  };
}

export interface CinemaSliceState {
  cinemas: Cinema[];
  hasMore: boolean;
  page: number;

  createdCinema?: Cinema;

  isLoading: boolean;
  isLoadingMore: boolean;
  isCreating: boolean;

  fetchErrorMessage?: string;
  createErrorMessage?: string;
}