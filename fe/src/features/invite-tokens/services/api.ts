import axiosInstance from "../../../lib/axiosInstance";
import type { GenerateTokensDto, InviteToken, InviteTokenCreated } from "../types";

const INVITE_TOKENS_BASE_PATH = "/invite-tokens";

export const fetchInviteTokens = async (): Promise<InviteToken[]> => {
  const res = await axiosInstance.get(INVITE_TOKENS_BASE_PATH);
  return res.data;
};

export const fetchGenerateTokens = async (
  dto: GenerateTokensDto
): Promise<InviteTokenCreated[]> => {
  const res = await axiosInstance.post(INVITE_TOKENS_BASE_PATH, dto);
  return res.data;
};