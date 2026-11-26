import { Link, NavLink } from "react-router-dom";
import { Button } from "@/components/ui/button";

const linkClass = ({ isActive }: { isActive: boolean }) =>
  isActive
    ? "text-sm font-medium text-slate-900"
    : "text-sm font-medium text-slate-500 hover:text-slate-900";

const Navbar = () => {
  return (
    <header className="border-b bg-white">
      <nav className="mx-auto flex h-16 max-w-5xl items-center justify-between px-6">
        {/* Left: logo + page links */}
        <div className="flex items-center gap-8">
          <Link to="/" className="text-lg font-bold text-slate-900">
            PayFlowX
          </Link>
          <NavLink to="/" className={linkClass}>
            Home
          </NavLink>
          <NavLink to="/profile" className={linkClass}>
            Profile
          </NavLink>
           <NavLink to="/about" className={linkClass}>
            About
          </NavLink>
        </div>

        {/* Right: login / sign up buttons */}
        <div className="flex items-center gap-3">
          <Button variant="ghost" render={<Link to="/login" />}>
            Log in
          </Button>
          <Button render={<Link to="/register" />}>Sign up</Button>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
