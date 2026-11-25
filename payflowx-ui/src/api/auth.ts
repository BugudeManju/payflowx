import client from "./client";

export async function login(email: string, password: string) {
  const response = await client.post("/api/auth/login", { email, password });
  return response.data;
}

export async function register(name: string, email: string, password: string) {
  const response = await client.post("/api/auth/register", { name,email, password });
}