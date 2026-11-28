import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

// The "TV frame": Navbar on top of every page, and the current page in the middle
const Layout = () => {
  return (
    <div className="min-h-screen bg-slate-50">
      <Navbar />
      <main className="mx-auto max-w-8xl p-6">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
