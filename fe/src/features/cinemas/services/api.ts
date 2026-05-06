import axiosInstance from "../../../lib/axiosInstance";
import type { CinemaListResponse, Cinema, CinemaCreateDto } from "../types";

const CINEMA_BASE_PATH = "/cinemas";

export const fetchCinemas = async (
  page: number,
): Promise<CinemaListResponse> => {
  const res = await axiosInstance.get(CINEMA_BASE_PATH, {
    params: { page },
  });
  return res.data;
};

export const createCinema = async (dto: CinemaCreateDto): Promise<Cinema> => {
  const res = await axiosInstance.post(CINEMA_BASE_PATH, dto);
  return res.data;
};

export const  updateCinema = async (
    id: string,
    dto: CinemaCreateDto,
): Promise<Cinema>=>{
  const res = await  axiosInstance.put(`${CINEMA_BASE_PATH}/${id}`, dto);
  return res.data;
};

export const deleteCinema = async (id: string)=>{
  await axiosInstance.delete(`${CINEMA_BASE_PATH}/${id}`);
};