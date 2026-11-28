import { Link, NavLink, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useAuth } from "../context/AuthContext";

const linkClass = ({ isActive }: { isActive: boolean }) =>
  isActive
    ? "text-sm font-medium text-slate-900"
    : "text-sm font-medium text-slate-500 hover:text-slate-900";

const Navbar = () => {
  const auth = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    auth.logout();
    navigate("/login");
  };

  return (
    <header className="border-b bg-white">
      <nav className="mx-auto flex h-16 max-w-7xl items-center justify-between px-6">
        <div className="flex items-center gap-6">
          <Link to="/" className="text-lg font-bold text-slate-900">
            PayFlowX
          </Link>
          <NavLink to="/" className={linkClass}>
            Home
          </NavLink>
          {auth.token && (
            <NavLink to="/profile" className={linkClass}>
              Profile
            </NavLink>
          )}
          <NavLink to="/about" className={linkClass}>
            About
          </NavLink>
        </div>

        <div className="flex items-center gap-3">
          {auth.token ? (
            <Button variant="ghost" onClick={handleLogout}>
              Log out
            </Button>
          ) : (
            <>
              <Button variant="ghost" render={<Link to="/login" />}>
                Log in
              </Button>
              <Button render={<Link to="/register" />}>Sign up</Button>
            </>
          )}
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
