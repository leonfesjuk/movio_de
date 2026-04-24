export interface InviteToken {
  token: string;
  createdAt: string;
  usedAt: string | null;
  used: boolean;
}

export interface InviteTokenCreated {
  token: string;
  createdAt: string;
  usedAt: string | null;
  used: boolean;
}

export interface GenerateTokensDto {
  count: number;
}

export interface InviteTokensSliceState {
  tokens: InviteToken[];
  generatedTokens: InviteTokenCreated[];
  isLoading: boolean;
  isGenerating: boolean;
  generateErrorMessage?: string;
  fetchErrorMessage?: string;
}