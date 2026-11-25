import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

// The "TV frame": Navbar on top of every page, and the current page in the middle
const Layout = () => {
  return (
    <>
      <Navbar />
      <main className="page">
        {/* React Router puts the page that matches the address here */}
        <Outlet />
      </main>
    </>
  );
};

export default Layout;
