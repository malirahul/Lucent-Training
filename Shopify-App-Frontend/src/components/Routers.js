import Products from "../components/Products";
import Customer from "../components/Customer";
import LogIn from "./LogOut";
import { Routes, Route } from "react-router-dom";
import Success from "./Success";

const Routers = () => {
  return (
    <Routes>
      <Route path="/success" element={<Success />} />
    </Routes>
  );
};

export default Routers;
