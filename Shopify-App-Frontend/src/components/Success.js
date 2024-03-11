import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Success() {
  const navigate = useNavigate();
  useEffect(() => {
    const authToken = sessionStorage.getItem("authToken");
    if (authToken === null) {
      window.location.href = "http://localhost:3000/login";
    } else {
      navigate("/home");
    }
  }, [navigate]);

  return <div></div>;
}
