import client from "./client";

export async function getMe() {
  const response = await client.get("/api/users/me");
  return response.data;
}

export default getMe;
