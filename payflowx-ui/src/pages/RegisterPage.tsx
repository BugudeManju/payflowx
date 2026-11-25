import React from "react";
import { useState } from "react";
import { register } from "../api/auth";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";

const RegisterPage = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // "browser, DON'T reload, I've got this"
    const data = await register(name, email, password);
    navigate("/login");
    console.log(name, email, password); // for now, just prove we can read the values
  };
  return (
    <div>
      <form onSubmit={handleSubmit}>
        <h1>Register New User</h1>
        <label htmlFor="name">Name</label>
        <Input
          id="name"
          type="name"
          value={name} // the box SHOWS what's in state
          onChange={(e) => setName(e.target.value)}
        />
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
       <Button type="submit">Register</Button>
      </form>
    </div>
  );
};

export default RegisterPage;
