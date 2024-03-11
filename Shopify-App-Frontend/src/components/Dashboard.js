import React, { useEffect } from "react";
import { Page, Layout, EmptyState } from "@shopify/polaris";

export default function Dashboard() {
  useEffect(() => {
    console.log("Going for Token");

    fetch("http://localhost:3000/token", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Data Object", data.data);
        console.log("Jwt Token", data);

        sessionStorage.setItem("authToken", data.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <Page
      fullWidth
      style={{
        height: "100%",
        fullWidth: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <EmptyState
        heading="Welcome to my Shopify app"
        image="https://c0.wallpaperflare.com/preview/60/449/302/business-professional-ecommerce-plants.jpg"
      ></EmptyState>
    </Page>
  );
}
