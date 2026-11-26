import React from "react";
import { useState } from "react";
import { register } from "../api/auth";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/Button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Link } from "react-router-dom";
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";

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
    <div className="flex justify-center pt-16">
      <Card className="w-full max-w-sm">
        {" "}
        {/* narrow card */}
        <CardContent>
          <form onSubmit={handleSubmit}>
            <FieldGroup>
              {" "}
              {/* even space between the rows */}
              <h1 className="text-center text-2xl font-bold">
                Register New User
              </h1>
              <Field>
                <FieldLabel htmlFor="name">Name</FieldLabel>
                <Input
                  id="name"
                  type="name"
                  value={name} // the box SHOWS what's in state
                  onChange={(e) => setName(e.target.value)}
                />
              </Field>
              <Field>
                <label htmlFor="email">Email</label>
                <Input
                  id="email"
                  type="email"
                  value={email} // the box SHOWS what's in state
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Field>
              <Field>
                <label htmlFor="password">Password</label>
                <Input
                  id="password"
                  type="password"
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </Field>
              <Button type="submit">Register</Button>
              <FieldDescription className="text-center">
                Already have an account? <Link to="/login">Login</Link>
              </FieldDescription>
            </FieldGroup>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default RegisterPage;
