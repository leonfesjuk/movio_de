import { createAppSlice } from "../../../app/createAppSlice";
import type { GenerateTokensDto, InviteTokensSliceState } from "../types";
import { isAxiosError } from "axios";
import * as api from "../services/api";

const initialState: InviteTokensSliceState = {
  tokens: [],
  generatedTokens: [],
  isLoading: false,
  isGenerating: false,
};

export const inviteTokensSlice = createAppSlice({
  name: "inviteTokens",
  initialState,
  reducers: (create) => ({
    fetchAllTokens: create.asyncThunk(
      async () => {
        return api.fetchInviteTokens().catch((err) => {
          if (isAxiosError(err)) {
            throw new Error(
              err.response?.data?.message || "Failed to load tokens"
            );
          }
          throw err;
        });
      },
      {
        pending: (state) => {
          state.isLoading = true;
          state.fetchErrorMessage = undefined;
        },
        fulfilled: (state, action) => {
          state.isLoading = false;
          state.tokens = action.payload;
        },
        rejected: (state, action) => {
          state.isLoading = false;
          state.fetchErrorMessage = action.error.message;
        },
      }
    ),

    generateTokens: create.asyncThunk(
      async (dto: GenerateTokensDto) => {
        return api.fetchGenerateTokens(dto).catch((err) => {
          if (isAxiosError(err)) {
            throw new Error(
              err.response?.data?.message || "Failed to generate tokens"
            );
          }
          throw err;
        });
      },
      {
        pending: (state) => {
          state.isGenerating = true;
          state.generateErrorMessage = undefined;
          state.generatedTokens = [];
        },
        fulfilled: (state, action) => {
          state.isGenerating = false;
          state.generatedTokens = action.payload;
          state.tokens = [...action.payload, ...state.tokens];
        },
        rejected: (state, action) => {
          state.isGenerating = false;
          state.generateErrorMessage = action.error.message;
        },
      }
    ),

    clearGeneratedTokens: create.reducer((state) => {
      state.generatedTokens = [];
    }),
  }),

  selectors: {
    selectTokens: (state) => state.tokens,
    selectGeneratedTokens: (state) => state.generatedTokens,
    selectIsLoading: (state) => state.isLoading,
    selectIsGenerating: (state) => state.isGenerating,
    selectGenerateErrorMessage: (state) => state.generateErrorMessage,
    selectFetchErrorMessage: (state) => state.fetchErrorMessage,
  },
});

export const { fetchAllTokens, generateTokens, clearGeneratedTokens } =
  inviteTokensSlice.actions;

export const {
  selectTokens,
  selectGeneratedTokens,
  selectIsLoading,
  selectIsGenerating,
  selectGenerateErrorMessage,
  selectFetchErrorMessage,
} = inviteTokensSlice.selectors;