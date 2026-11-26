import { NavLink } from "react-router-dom";

// The header bar. Each NavLink is a button that changes the address (no page reload)
const Navbar = () => {
  return (
    <nav className="bg-slate-900">
      <span className="text-xl font-bold text-white">PayFlowX</span>
      <div className="links">
        <NavLink to="/" className="text-white">
          Home
        </NavLink>
        <NavLink to="/profile" className="text-white">
          Profile
        </NavLink>
        <NavLink to="/login" className="text-white">
          Login
        </NavLink>
        <NavLink to="/register" className="text-white">
          Sign up
        </NavLink>
        <NavLink to="/about" className="text-white">
          About
        </NavLink>
      </div>
    </nav>
  );
};

export default Navbar;
