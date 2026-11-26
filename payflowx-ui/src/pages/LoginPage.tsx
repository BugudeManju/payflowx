import React from "react";
import { useState } from "react";
import { login } from "../api/auth";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Link } from "react-router-dom";
import { Input } from "@/components/ui/input";
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";

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
            <FieldGroup>
              {" "}
              {/* even space between the rows */}
              <h1 className="text-center text-2xl font-bold">Welcome back</h1>
              <Field>
                {" "}
                {/* one row = label + box */}
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
                  id="email"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </Field>
              <Field>
                <FieldLabel htmlFor="password">Password</FieldLabel>
                <Input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </Field>
              <Button type="submit">Login</Button>
              <FieldDescription className="text-center">
                Don't have an account? <Link to="/register">Sign up</Link>
              </FieldDescription>
            </FieldGroup>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default LoginPage;
