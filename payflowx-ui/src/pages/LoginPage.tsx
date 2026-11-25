import React from "react";
import { useState } from "react";
import { login } from "../api/auth";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/Button";
import { Card, CardContent } from "@/components/ui/card";

import { Input } from "@/components/ui/input";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // "browser, DON'T reload, I've got this"
    const data = await login(email, password);
    localStorage.setItem("token", data.token); // save:  put(key, value)
    localStorage.getItem("token");
    navigate("/");
    console.log(email, password); // for now, just prove we can read the values
  };
  return (
    <div className="flex justify-center pt-16">
      <Card className="w-full max-w-sm">
        {" "}
        {/* narrow card */}
        <CardContent>
          <form onSubmit={handleSubmit}>
            <h1>Login Page</h1>
            <label htmlFor="email">Email</label>
            <Input
              id="email"
              type="email"
              value={email} // the box SHOWS what's in state
              onChange={(e) => setEmail(e.target.value)}
            />
            <label htmlFor="password">Password</label>
            <Input
              id="password"
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            />
            <Button type="submit">Login</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default LoginPage;
