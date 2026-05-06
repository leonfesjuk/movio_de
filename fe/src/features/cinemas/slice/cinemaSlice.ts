import { createAppSlice } from "../../../app/createAppSlice";
import type { CinemaCreateDto, CinemaSliceState } from "../types";
import { isAxiosError } from "axios";
import * as api from "../services/api";

const initialState: CinemaSliceState = {
  cinemas: [],
  hasMore: false,
  page: 0,

  createdCinema: undefined,
  isLoading: false,
  isLoadingMore: false,
  isCreating: false,
};

export const cinemaSlice = createAppSlice({
  name: "cinema",
  initialState,
  reducers: (create) => ({
    fetchAllCinemas: create.asyncThunk(
      async () => {
        return api.fetchCinemas(0).catch((err) => {
          if (isAxiosError(err)) {
            throw new Error(
              err.response?.data?.message || "Failed to load cinemas",
            );
          }
          throw err;
        });
      },
      {
        pending: (state) => {
          state.isLoading = true;
          state.fetchErrorMessage = undefined;
          state.page = 0;
        },
        fulfilled: (state, action) => {
          state.isLoading = false;
          state.cinemas = action.payload.items;
          state.hasMore = action.payload.pagination.hasMore;
          state.page = 1;
        },
        rejected: (state, action) => {
          state.isLoading = false;
          state.fetchErrorMessage = action.error.message;
        },
      },
    ),

    loadMoreCinemas: create.asyncThunk(
      async (_, { getState }) => {
        const state = getState() as {cinema: CinemaSliceState};
        const page = state.cinema.page;

        return api.fetchCinemas(page);
      },
      {
        pending: (state) => {
          state.isLoadingMore = true;
        },
        fulfilled: (state, action) => {
          state.isLoadingMore = false;
          state.cinemas = [...state.cinemas, ...action.payload.items];
          state.hasMore = action.payload.pagination.hasMore;
          state.page += 1;
        },
        rejected: (state) => {
          state.isLoadingMore = false;
        },
      },
    ),

    createCinema: create.asyncThunk(
      async (dto: CinemaCreateDto) => {
        return api.createCinema(dto);
      },
      {
        pending: (state) => {
          state.isCreating = true;
          state.createErrorMessage = undefined;
        },
        fulfilled: (state, action) => {
          state.isCreating = false;
          state.createdCinema = action.payload;
          state.cinemas = [action.payload, ...state.cinemas];
        },
        rejected: (state, action) => {
          state.isCreating = false;
          state.createErrorMessage = action.error.message;
        },
      },
    ),

    updateCinema: create.asyncThunk(
        async ({ id, dto}: { id: string; dto: CinemaCreateDto }) =>{
          return api.updateCinema(id,dto);
        },
        {
          fulfilled: (state, action) => {
            state.cinemas = state.cinemas.map((c)=>
            c.id === action.payload.id ? action.payload : c,
                );
          },
        },
    ),

    deleteCinema: create.asyncThunk(
        async (id: string) => {
          await api.deleteCinema(id);
          return id;
        },
        {
          fulfilled: (state, action) => {
            state.cinemas = state.cinemas.filter(
                (c) => c.id !== action.payload,
            );
          },
        },
    ),

    clearCreatedCinema: create.reducer((state) => {
      state.createdCinema = undefined;
    }),
  }),

  selectors: {
    selectCinemas: (state) => state.cinemas,
    selectIsLoading: (state) => state.isLoading,
    selectFetchErrorMessage: (state) => state.fetchErrorMessage,
    selectHasMore: (state) => state.hasMore,
    selectIsLoadingMore: (state) => state.isLoadingMore,
    selectIsCreating: (state) => state.isCreating,
    selectCreateErrorMessage: (state) => state.createErrorMessage,
  },
});

export const {
  fetchAllCinemas,
  loadMoreCinemas,
  createCinema,
  updateCinema,
  deleteCinema,
  clearCreatedCinema,
} = cinemaSlice.actions;

export const {
  selectCinemas,
  selectIsLoading,
  selectFetchErrorMessage,
  selectHasMore,
  selectIsLoadingMore,
  selectIsCreating,
  selectCreateErrorMessage,
} = cinemaSlice.selectors;
