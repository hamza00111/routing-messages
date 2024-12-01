import {environment} from "../environment";

const apiUrl = environment.backendUrl;

export const API_ENDPOINTS = {
  MESSAGES: `${apiUrl}/api/v1/messages`,
  PARTNERS: `${apiUrl}/api/v1/partners`
} as const;
